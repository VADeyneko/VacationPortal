package web;

import beans.RequestService;
import beans.UserService;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import web.core.AbstractServlet;
import dao.RequestDao;
import dao.RequestStateDao;
import dao.VacationTypeDao;
import exceptions.PrimaryKeyViolationException;
import forms.RequestForm;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.ejb.EJB;
import javax.validation.ValidationException;
import model.Request;
import model.RequestState;
import model.User;
import model.VacationType;

@WebServlet(name = "RequestServlet", urlPatterns = {"/requestList", "/requestEdit", "/requestDelete", "/requestInsert", "/requestDetails", "/requestListManager", "/requestForward"})
public class RequestServlet extends AbstractServlet {

    @Inject
    protected HttpServletRequest request;

    @Inject
    protected RequestService service;

    @Inject
    protected UserService userService;

    @Inject
    protected RequestForm form;

    protected @EJB
    RequestDao dao;

    protected @EJB
    VacationTypeDao vacationTypeDao;

    protected @EJB
    RequestStateDao requestStateDao;
    
    private Request objToEdit;

    private User requestOwner;

    private String action;
    private final String objListPath = "requestList";
    private final String formType = "request";
    private final String DISABLED = "disabled";
    private final String READONLY = "readonly";

    private boolean byManager;

    @Override
    protected void doGet() throws ServletException, IOException {
        action = request.getServletPath().substring(1);
        request.setAttribute("formType", formType);
        request.setAttribute("action", action);  

 
            requestOwner = userService.getAuthUser();
            request.setAttribute("reqOwner", requestOwner);
 

        if (!action.contains(objListPath)) {

            request.setAttribute("newRequestDefaultDate", Request.getNewRequestDefaultDate());  //зададит текущую дату для инициализации календаря. иначе при "сохранении" будет ошибка

            try {
                if (request.getParameter("id") != null && form.getId() != null) { //для редактирования и удаления
                    objToEdit = service.findById(form.getId());
                    request.setAttribute("objToEdit", objToEdit);                    
                    request.setAttribute("detailsCollection", form.getDetailSummary(objToEdit));
                    request.setAttribute("possibleStates", objToEdit.getRequestState().getPossibleStates(byManager));
                    request.setAttribute("intersectionList", dao.findIntersections(objToEdit));

                    if (request.getParameter("toState") != null) {
                        String toState = request.getParameter("toState");
                        request.setAttribute("newState", service.getRequestState(toState));
                    }
                }

            } catch (IllegalArgumentException d) {
                request.setAttribute("error", d.getLocalizedMessage());
            }

            disableEdit();    //Метод использует поле objToEdit. Есть проверка на Null
            fillDropDownControls(); // Заполняем строго ПОСЛЕ, т.к. метод использует Attribute "possibleStates".  + использует поле objToEdit

            forward("actions/" + action.substring(7));

        } else {
            if (action.equals(objListPath + "Manager")) {
                getServletContext().setAttribute(formType + "List", dao.findByManager(requestOwner));
                byManager = true;  //взводится флаг , что список по менеджеру. для дальнейшего использования в fillDropDownControls. ПОКА НЕ откроют список своих заявок
            } else {
                getServletContext().setAttribute(formType + "List", dao.findByOwner(requestOwner));
                byManager = false;
            }
            forward("request/" + objListPath);      // по умолчанию будет основной список  
        }
    }

    @Override
    protected void doPost() throws ServletException, IOException {
        action = request.getServletPath().substring(1);

        if   (action.equals(formType + "Forward"))  {
            Request objToSave = service.findById(form.getId());
            try {
                objToSave = form.forwardToState(objToSave);
                service.checkIntersectionByOwner(objToSave , action);
                service.update(objToSave);
            } catch (ValidationException d) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();
            }
        }
        
        
        if ((action.equals(formType + "Edit"))  ) {
            Request objToSave = service.findById(form.getId());
            try {
                 service.checkIntersectionByOwner(objToSave, action);
                objToSave = form.validateAndUpdate(objToSave);
                service.update(objToSave);
            } catch (ValidationException d) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();
            }
        }

        if (action.equals(formType + "Insert")) {
            try {
                Request req = form.convertTo(Request.class);
                service.checkIntersectionByOwner(req, action);
                service.register(req);                
            } catch (ValidationException d) {
                request.setAttribute("error", d.getLocalizedMessage());
                 doGet();
            } catch (PrimaryKeyViolationException ex) {
                request.setAttribute("error", "already exists");
                 doGet();
            }
        }

        if (action.contains(formType + "Delete")) {
            Request req = service.findById(form.getId());
            try {
                service.delete(req);
            } catch (Exception e) {
                request.setAttribute("error", errorMessage("error.integrety-exception-fkrecords-found"));
                doGet();
            }
        }

        init();
     
        if(byManager)
          redirect("VacationPortal/" + objListPath + "Manager");
        else
            redirect("VacationPortal/" + objListPath);
    }

    //Заполнение выпадающих списков формы
    private void fillDropDownControls() {
         
        List<User> ownerList = new ArrayList<>();
        ownerList.add(requestOwner); //Без вариантов только один создатель
        request.setAttribute("ownerList", ownerList);

        List<User> managerList ;
        managerList = userService.findAll();
        request.setAttribute("managerList", managerList);

        LinkedList<RequestState> requestStateList = new LinkedList<>();

        //Если вставка, то будет доступен ТОЛЬКО ОДИН вариант. Иначе - два
        if (action.equals(formType + "Insert") ||  objToEdit == null ) {
            requestStateList.addAll(requestStateDao.getInitialStateList());
            request.setAttribute("requestStateList", requestStateList);
        } else {           
            requestStateList.add(objToEdit.getRequestState());
            requestStateList.addAll(objToEdit.getRequestState().getPossibleStates(byManager));
            request.setAttribute("requestStateList", requestStateList);
        }

        // вспомогательный блок для определения в ReqeustForm.tag того,
        //какой элемент requestStateList выбран и Endabled
        if (request.getParameter("toState") != null) {
            request.setAttribute("selectedState_Id", request.getParameter("toState"));
        } else {
             try {
                   request.setAttribute("selectedState_Id", requestStateList.getFirst().getId());
                 } catch (NoSuchElementException ignore) {}
           
        }

        List<VacationType> vacationTypeList = vacationTypeDao.all();
        request.setAttribute("vacationTypeList", vacationTypeList);
    }

    /*вспомогательный метод*/
    private void disableEdit(){         
         String result  ="";
          
        request.setAttribute("isOwnerCommentReadonly", isOwnerCommentReadonly());  
        request.setAttribute("isManagerCommentReadonly", isManagerCommentReadonly());  
      
       
        
         if(objToEdit != null)   {
                if( ( action.equals(formType + "Delete")) || objToEdit.isIsHistoryEntity()  ) {
                    result = DISABLED ;
                }               

                if(objToEdit.getRequestState().getId() > 1L){
                      result = DISABLED;
                }
                                 
         }
         
         if(action.contains("Insert"))
             result = "";
                  
          request.setAttribute("dsbl_all", result); //управление доступностью ВСЕХ полей для удаления
      
    }
    
    
   private  String isOwnerCommentReadonly (){
       if (objToEdit == null) return "";
           
       if (objToEdit.isIsHistoryEntity() || byManager  )
                   return  READONLY;
       
       return  (objToEdit.getRequestState().getId() == 1)  ? "" : READONLY ;
   }
       
   private  String isManagerCommentReadonly (){
        if (objToEdit == null) return  READONLY;
           
        if( action.contains("Insert"))
            return  READONLY ;
        
        if (objToEdit.isIsHistoryEntity() || !byManager )
                return  READONLY;
        
       return  (objToEdit.getRequestState().getId() == 1) ?  READONLY : "";
   }
     
}
