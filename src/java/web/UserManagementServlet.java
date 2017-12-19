package web;

import web.core.AbstractServlet;
import beans.UserService;
import dao.UserGroupDao;
import exceptions.PrimaryKeyViolationException;
import forms.UserForm;
import forms.core.FormParamProps;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import model.User;
import model.UserGroup;

@WebServlet(name = "UserManagementServlet", urlPatterns = {"/userManagement", "/userEdit", "/userDelete", "/userInsert", "/userDetails", "/changePassword"})
public class UserManagementServlet extends AbstractServlet {

    @Inject
    protected UserService service;
    
    @EJB
    protected UserGroupDao userGroupDao;

    @Inject
    protected HttpServletRequest request;

    @Inject
    protected UserForm form;
    
    private String action;
    private final String objListPath = "userManagement";
    private final String formType ="user";
    private FormParamProps paramProps;

    @Override
    protected void doGet() throws ServletException, IOException {

        action = request.getServletPath().substring(1);
        request.setAttribute("formType", formType );     

        //если не авторизован - иди вводи пароль
        if (!service.isAuth()) {
            forward("auth/login");
        }

        //если не userManagement , то значит CRUD операция
        if (!action.equals(objListPath)) {
             paramProps = form.initParamProps();
             
             fillDropDownControls();           
             request.setAttribute("action", action);     
             
            if (request.getParameter("id") != null) { //для редактирования и удаления
                User objToEdit;
                
                if( request.getParameter("id").isEmpty()){
                  objToEdit =  (User)(request.getSession().getAttribute("sessionUser")); //if id is empty user can change the password of him/herself
                       paramProps.setReadonly("lastname", true); 
                       paramProps.setReadonly("name", true);
                       paramProps.setReadonly("email", true);
                       paramProps.setHidden("manager", true);                        
                       paramProps.setHidden("userGroup", true);
                       
                        
                       request.setAttribute("formParamProps", paramProps);
                }else {
                  objToEdit = service.findById(form.getId());
                }                
                request.setAttribute("objToEdit", objToEdit);
                request.setAttribute("dsbl_all", action.equals(formType+"Delete") ? "disabled": ""); //управление доступностью ВСЕХ полей для удаления
                request.setAttribute("detailsCollection", form.getDetailSummary(objToEdit));                   
               }  
            
            forward("actions/" + action.substring(4) );
             
        }  else {              
        forward("auth/" + objListPath);      // по умолчанию будет основной список  
        }       
    }

    @Override
    protected void doPost() throws ServletException, IOException {

        action = request.getServletPath().substring(1);

        if (action.equals(formType + "Edit")) {
            User userToSave = service.findById(form.getId());
            try {
                form.validateAndUpdate(userToSave);
            } catch (ValidationException d ) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();
          } catch (AccountNotFoundException d ) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();   
            }         
        }

        if (action.equals(formType +"Delete")) {
            User userToSave = service.findById(form.getId());
            try{
            service.delete(userToSave);
            } catch (Exception e){
                 request.setAttribute("error", errorMessage("error.integrety-exception-fkrecords-found"));
                 doGet();
            }
        }

        if (action.equals(formType +"Insert")) {
            try {
                User user = form.convertTo(User.class);
                service.register(user);

            } catch (ValidationException d) {
                request.setAttribute("error", d.getLocalizedMessage());
                 doGet();
             } catch (NoSuchAlgorithmException v) {
                request.setAttribute("error", v.getLocalizedMessage());
                 doGet();     
            } catch (PrimaryKeyViolationException e ) {
                request.setAttribute("error", errorMessage("error.account.user-already-exists"));
                 doGet();
            }
           
        }   
        init();
        redirect("VacationPortal/" + objListPath);

    }

    @Override
    public void init() throws ServletException {
        getServletContext().setAttribute(formType +"list", service.findAll());       
    }
    
    //Заполнение выпадающих списков формы
    private void fillDropDownControls(){
            List<UserGroup> userGroupList = userGroupDao.all();      //передаем перечень групп для вып. списка   
            request.setAttribute("userGroupList", userGroupList);
            
            List<User> managerList = service.findAll();      //передаем перечень групп для вып. списка   
            request.setAttribute("managerList", managerList);
    }

}
