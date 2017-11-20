package web;

import web.core.AbstractServlet;
import beans.UserService;
import exceptions.PrimaryKeyViolationException;
import forms.UserForm;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import model.User;

@WebServlet("/registration")
public class RegistrationServlet extends AbstractServlet {
 
    @Inject
    protected HttpServletRequest request;
    
    @Inject
    protected UserService service;
    
    @Inject
    protected  UserForm form;
    @Override
    protected void doGet() throws ServletException, IOException {
        forward("auth/registration");
        
    }

    @Override
    protected void doPost() throws ServletException, IOException {
        try {
            User user = form.convertTo(User.class);
            service.register(user);
            service.singIn(user.getCredentials());
             redirect("VacationPortal/index");
        } catch (ValidationException | PrimaryKeyViolationException e  ) {
            request.setAttribute("error", errorMessage("error.account.user-already-exists"));
        } catch (AccountNotFoundException ex) {
            request.setAttribute("error", errorMessage("error.account.account-processed-but-not-found"));
        }
         doGet();
    }
    
}
