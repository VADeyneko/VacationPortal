 
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.validation.ValidationException;
import model.Request;
import model.RequestState;
import model.User;
import model.VacationType;

@WebServlet(urlPatterns = {"/requestList", "/requestEdit", "/requestDelete", "/requestInsert"})
public class RequestServlet   extends AbstractServlet{
    
    @Inject
    protected HttpServletRequest request;
    
    @Inject
    protected RequestService requestService;
    
    @Inject
    protected UserService service;
    
    @Inject
    protected RequestForm form;
    
    protected @EJB RequestDao dao;
    
    protected @EJB VacationTypeDao vacationTypeDao;
     
    protected @EJB RequestStateDao requestStateDao;
    
    
    private String action;
    private   User requestOwner;
    
    private String objListPath = "requestList";

    @Override
    protected void doGet() throws ServletException, IOException {
          action = request.getServletPath().substring(1);

        //если не авторизован - иди вводи пароль
        if (!service.isAuth()) {
            forward("auth/login");
        } else {
            requestOwner  = service.getAuthUser();
            request.setAttribute("reqOwner",requestOwner);
        }
                
         if (!action.equals(objListPath)) {

            if (request.getParameter("id") != null) {
                Request objToEdit = requestService.findById( Long.parseLong(request.getParameter("id")  ) ) ;
                request.setAttribute("objToEdit", objToEdit);
                request.setAttribute("dsbl_all", action.equals("requestDelete") ? "disabled": ""); //управление доступностью ВСЕХ полей для удаления
                request.setAttribute("action", action);
                } else {
                request.setAttribute("action", action); // остается только Insert
            }
            
            fillDropDownControls();
            forward("request/" + action);
        }
         else { 
       forward("request/" + objListPath);
         }
    }
    
    
    @Override
    protected void doPost() throws ServletException, IOException {
    
          if (action.equals("requestInsert")) {              
            try {
                Request req = form.convertTo(Request.class);
                requestService.register(req);

            } catch (ValidationException d) {
                request.setAttribute("error", d.getLocalizedMessage());
                doGet();                  
            } catch (PrimaryKeyViolationException ex) {           
                  request.setAttribute("error", "already exists");
                   doGet(); 
              }  
            
        }  
          
        if (action.contains("requestDelete")) {
            Request req = requestService.findById(form.getId());
           try{
            requestService.delete(req);
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
        getServletContext().setAttribute("requestList", dao.all());       
    }
    
        
    //Заполнение выпадающих списков формы
    private void fillDropDownControls(){             
            List<User> userList = service.findAll();      //передаем перечень групп для вып. списка   
            request.setAttribute("userList", userList);         
            
         
            
            List<RequestState> requestStateList = requestStateDao.getInitialStateList();
            request.setAttribute("requestStateList", requestStateList);     
            
            List<VacationType> vacationTypeList = vacationTypeDao.all();
            request.setAttribute("vacationTypeList", vacationTypeList);    
    }
    
}
