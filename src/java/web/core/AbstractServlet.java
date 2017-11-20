package web.core;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractServlet extends HttpServlet {
    
    protected static final ResourceBundle ERRORS;
    
    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");
    }

    private HttpServletRequest request;
    
    private HttpServletResponse response;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet();
    }
    
    protected void doGet() throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost();
    }
    
    protected void doPost() throws ServletException, IOException {
        super.doPost(request, response);
    }

    protected void forward(String page) throws ServletException, IOException {
        page = "/WEB-INF/pages/" + page + ".jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    protected void redirect(String address) {
        address = "/" + address;
        response.setHeader("Location", address);
        response.setStatus(302);
    }
    
    protected String errorMessage(String name) {
        return ERRORS.getString(name);
    }
    
} 