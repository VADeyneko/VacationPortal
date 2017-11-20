package web;

import web.core.AbstractServlet;
import beans.UserService;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/index")
public class IndexServlet extends AbstractServlet {

    @Inject
    protected UserService service;
    
    @Override
    protected void doGet() throws ServletException, IOException {
      
        if (service.isAuth()) {
                forward("/index");
        } else {             
            forward("auth/login");
             
        }
        
        
    }
    
}
