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
import forms.core.FormParamProps;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.validation.ValidationException;
import model.Request;
import model.RequestState;
import model.User;

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
 

    private boolean byManager;

    FormParamProps paramProps;
      


    @Override
    protected void doGet() throws ServletException, IOException {
        action = request.getServletPath().substring(1);
        request.setAttribute("formType", formType);
        request.setAttribute("action", action);
    
        requestOwner = userService.getAuthUser();
        request.setAttribute("reqOwner", requestOwner);

       
            
        if (!action.contains(objListPath)) {
             paramProps = form.initParamProps();

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
                    
                    if (!(objToEdit.getManager().equals(requestOwner) || objToEdit.getOwner().equals(requestOwner) ))
                         forward( "auth/restricted");
                }

            } catch (IllegalArgumentException d) {
                request.setAttribute("error", d.getLocalizedMessage());
            }

            disableEdit();    //Метод использует поле objToEdit. Есть проверка на Null
            fillDropDownControls(); // Заполняем строго ПОСЛЕ, т.к. метод использует Attribute "possibleStates".  + использует поле objToEdit

            paramProps.setDisabled("requestState", false);
            paramProps.setReadonly("requestState", true);
            paramProps.setDisabled("date-range0-container", true);
            request.setAttribute("formParamProps", paramProps);

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

        if (action.equals(formType + "Forward")) {
            Request objToSave = service.findById(form.getId());
            try {                
                RequestState newState = service.getRequestState(form.getRequestState());
                service.forwardToState(objToSave, newState, form.getManagerComment());
                
                service.checkIntersectionByOwner(objToSave, action);
                service.update(objToSave);
                service.notifyUser(objToSave, request.getRequestURL()+ "?"  +request.getQueryString());
            } catch (ValidationException | MessagingException d) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();                
            }
        }

        if ((action.equals(formType + "Edit"))) {
            Request objToSave = service.findById(form.getId());
            try {
                service.checkIntersectionByOwner(objToSave, action);
                objToSave = form.validateAndUpdate(objToSave);
                service.validateAgainstAppSettings(objToSave);
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
                service.validateAgainstAppSettings(req);
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

        if (byManager) {
            redirect("VacationPortal/" + objListPath + "Manager");
        } else {
            redirect("VacationPortal/" + objListPath);
        }
    }

    //Заполнение выпадающих списков формы
    private void fillDropDownControls() {

        form.fillOwnerDropdown(requestOwner);
        form.fillManagerDropdown();

        //Если вставка, то будет доступен ТОЛЬКО ОДИН вариант. Иначе - два
        if (objToEdit == null || action.equals(formType + "Insert")) {
            form.fillRequestStateDropdown(null, byManager);
        } else {
            form.fillRequestStateDropdown(objToEdit, byManager);
        }

        form.fillVacationTypeDropdown();
    }

    /*вспомогательный метод*/
    private void disableEdit() {
        
        paramProps.setReadonly("managerComment", isManagerCommentReadonly());
        paramProps.setReadonly("ownerComment", isOwnerCommentReadonly());

        if (objToEdit != null) {
            if ((action.equals(formType + "Delete")) || objToEdit.isIsHistoryEntity()) {

                paramProps.setAllDisabled(true);
            }

            if (objToEdit.getRequestState().getId() > 1L) {

                paramProps.setAllDisabled(true);
            }
        }


        paramProps.setDisabled("managerComment", false);
        paramProps.setDisabled("ownerComment", false);

        if (action.contains("Insert")) {
            paramProps.setAllDisabled(false);
        }

    }

    private boolean isOwnerCommentReadonly() {
        if (objToEdit == null) {
            return false;
        }

        if (objToEdit.isIsHistoryEntity() || byManager) {
            return true;
        }

        return (objToEdit.getRequestState().getId() != 1);
    }

    private boolean isManagerCommentReadonly() {
        if (objToEdit == null) {
            return false;
        }

        if (action.contains("Insert")) {
            return true;
        }

        if (objToEdit.isIsHistoryEntity() || !byManager) {
            return true;
        }

        return (objToEdit.getRequestState().getId() == 1);
    }

}
