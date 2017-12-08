/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import beans.UserService;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author admin
 */
@WebFilter(filterName = "authFilter", servletNames = {"UserManagementServlet",  "RequestServlet",  "ReportServlet"})
public class authFilter implements Filter {

    @Inject
    private UserService service;

    public authFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        
        User user = (User) req.getSession().getAttribute("sessionUser");

        if (user == null || !service.isAuth()) {
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            if (service.hasAccessToMenuItem(req.getServletPath())) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + "/restricted");
            }

        }

    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {

    }

}
