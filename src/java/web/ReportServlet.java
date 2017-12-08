 
package web;

import forms.RequestForm;
import forms.core.FormParamProps;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import model.Request;
import web.core.AbstractServlet;

/**
 *
 * @author Victor Deyneko <VADeyneko@gmail.com>
 */
@WebServlet(name = "ReportServlet", urlPatterns = {"/requestReport"})
public class ReportServlet extends AbstractServlet {
 
   @Inject
   protected HttpServletRequest request;
   
    @Inject
    protected RequestForm form;
   
   private String action;
   
   private FormParamProps paramProps;
   
    @Override
    protected void doGet() throws ServletException, IOException {
          action = request.getServletPath().substring(1);
          request.setAttribute("action", action);
          request.setAttribute("newRequestDefaultDate", Request.getNewRequestDefaultDate()); 
          
          request.setAttribute("notDefinedAllowed", true);

          form.fillRequestStateDropdown();
          form.fillVacationTypeDropdown();
          form.fillManagerDropdown();
          form.fillOwnerDropdown();
          
          paramProps = form.initParamProps();
          paramProps.setAllDisabled(false);   
          paramProps.setHidden("ownerComment", true);
          paramProps.setReadonly("requestOwner", false);
          
          request.setAttribute("formParamProps", paramProps);
           
          forward("report/requestReport");
    }
    
}
