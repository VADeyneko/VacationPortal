package web;

import dao.RequestDao;
import forms.RequestForm;
import forms.core.FormParamProps;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
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

    protected @EJB
    RequestDao dao;

    @Override
    protected void doGet() throws ServletException, IOException {
        action = request.getServletPath().substring(1);
        request.setAttribute("action", action);
        request.setAttribute("newRequestDefaultDate", Request.getNewRequestDefaultDate());

        request.setAttribute("notDefinedAllowed", true);

        paramProps = form.initParamProps();
        form.fillRequestStateDropdown();
        form.fillVacationTypeDropdown();
        form.fillManagerDropdown();
        form.fillOwnerDropdown();

        paramProps.setAllDisabled(false);
        paramProps.setHidden("ownerComment", true);
        paramProps.setReadonly("requestOwner", false);

        request.setAttribute("formParamProps", paramProps);

        forward("report/requestReport");
    }

    @Override
    protected void doPost() throws ServletException, IOException {
        action = request.getServletPath().substring(1);

        if (action.contains("requestReport")) {
            Long vacationTypeId = form.getVacationTypeId();
            Long managerId = form.getManagerId();
            Long ownerId = form.getOwnerId();
            Long requestStateId = form.getRequestStateId();
            Date validatedDateBegin = form.getValidatedDateBegin();
            Date validatedDateEnd = form.getValidatedDateEnd();

            List<Request> reportList;
            reportList = dao.getReport(vacationTypeId, managerId, ownerId, requestStateId, validatedDateBegin, validatedDateEnd);
            request.setAttribute("isParameterDivHidden", "hidden");
            request.setAttribute("reportList", reportList);
        }
        doGet();
    }
}
