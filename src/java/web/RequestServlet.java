 
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
import java.util.List;
import javax.ejb.EJB;
import javax.validation.ValidationException;
import model.Request;
import model.RequestState;
import model.User;
import model.VacationType;

@WebServlet(urlPatterns = {"/requestList", "/requestEdit", "/requestDelete", "/requestInsert", "/requestDetails"})
public class RequestServlet   extends AbstractServlet{
    
    @Inject
    protected HttpServletRequest request;
    
    @Inject
    protected RequestService service;
    
    @Inject
    protected UserService userService;
    
    @Inject
    protected RequestForm form;
    
    protected @EJB RequestDao dao;
    
    protected @EJB VacationTypeDao vacationTypeDao;
     
    protected @EJB RequestStateDao requestStateDao;
    
    
    private   User requestOwner;
    
    private String action;
    private final String objListPath = "requestList";
    private final String formType ="request";    
    

    @Override
    protected void doGet() throws ServletException, IOException {
         action = request.getServletPath().substring(1);
         request.setAttribute("formType", formType );    

        //если не авторизован - иди вводи пароль
        if (!userService.isAuth()) {
            forward("auth/login");
        } else {
            requestOwner  = userService.getAuthUser();
            request.setAttribute("reqOwner",requestOwner);
        }
                
        
        
         if (!action.equals(objListPath)) {
            fillDropDownControls();
            request.setAttribute("action", action);     
             request.setAttribute("newRequestDefaultDate", Request.getNewRequestDefaultDate());  //зададит текущую дату для инициализации календаря. иначе при "сохранении" будет ошибка
            
           if (request.getParameter("id") != null) { //для редактирования и удаления
                Request objToEdit = service.findById(form.getId());
                request.setAttribute("objToEdit", objToEdit);
                //request.setAttribute("formattedDate", objToEdit.getFormatedDateBegin());
                request.setAttribute("dsbl_all", action.equals(formType+"Delete") ? "disabled": ""); //управление доступностью ВСЕХ полей для удаления
                request.setAttribute("detailsCollection", form.getDetailSummary(objToEdit));                   
               }  
           
            
            forward("actions/" + action.substring(7) );
             
        }  else {              
        forward("request/" + objListPath);      // по умолчанию будет основной список  
        }       
    }
    
    
    @Override
    protected void doPost() throws ServletException, IOException {
        action = request.getServletPath().substring(1);    
        
          if (action.equals(formType + "Edit")) {
            Request objToSave = service.findById(form.getId());
            try {
                form.validateAndUpdate(objToSave);
            } catch (ValidationException d ) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();
          }       
        }
    
          if (action.equals(formType+"Insert")) {              
            try {
                Request req = form.convertTo(Request.class);
                service.register(req);

            } catch (ValidationException d) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();                  
            } catch (PrimaryKeyViolationException ex) {           
                  request.setAttribute("error", "already exists");
                   doGet(); 
              }  
            
        }  
          
        if (action.contains(formType+"Delete")) {
            Request req = service.findById(form.getId());
           try{
            service.delete(req);
            } catch (Exception e){
                 request.setAttribute("error", errorMessage("error.integrety-exception-fkrecords-found"));
                 doGet();
            }          
        }
        
        init();       
           redirect("VacationPortal/" + objListPath);
    }

    @Override
    public void init() throws ServletException {
        getServletContext().setAttribute(formType+"List", dao.all());       
    }
    
        
    //Заполнение выпадающих списков формы
    private void fillDropDownControls(){             
            List<User> userList = new ArrayList<User>();      
            userList.add(requestOwner); //Без вариантов только один создатель
            request.setAttribute("userList", userList);         
            
         
            
            List<RequestState> requestStateList = requestStateDao.getInitialStateList();
            
            //Если вставка, то будет доступен ТОЛЬКО ОДИН вариант. Иначе - два
            if(action.equals(formType+"Insert")){
             requestStateList.remove(1);
                 request.setAttribute("requestStateList",requestStateList );  
            }else{                
            request.setAttribute("requestStateList", requestStateList);  
            }
             
            List<VacationType> vacationTypeList = vacationTypeDao.all();
            request.setAttribute("vacationTypeList", vacationTypeList);    
    }
    
}
