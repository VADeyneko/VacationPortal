package web;

import beans.UserGroupService;
import web.core.AbstractServlet;
import beans.UserService;
import forms.LoginForm;
import java.io.IOException;
import javax.inject.Inject;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import model.Credentials;

@WebServlet( urlPatterns = {"/login", "/logout"})
public class LoginServlet extends AbstractServlet {
    
    @Inject HttpServletRequest request;
    
    @Inject
    protected LoginForm form;
    
    @Inject
    protected UserService service;
 
    @Override
    protected void doGet() throws ServletException, IOException {
       
        if(request.getServletPath().equals("/logout")) {         
            service.signOut();
        }
            
          
        forward("auth/login");
    }

    @Override
    protected void doPost() throws ServletException, IOException {
 
        try {
            Credentials credentials = form.convertTo(Credentials.class);
            service.singIn(credentials);
         
             redirect("VacationPortal/userManagement");
        } catch (ValidationException e) {
            request.setAttribute("error", e.getMessage());
        } catch (AccountNotFoundException e) {
            request.setAttribute("error", errorMessage("error.account.user-not-found"));
        }
        doGet();
        
    }
    
    
}
