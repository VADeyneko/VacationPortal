package forms;

import dao.AbstractDao;
import dao.RequestStateDao;
import dao.UserDao;
import dao.VacationTypeDao;
import forms.core.Convertable;
import java.util.ResourceBundle;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import model.Request;
import model.User;
import static forms.core.Validation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import model.RequestState;
import model.VacationType;

/**
 *
 * @author admin
 */
@Named
@RequestScoped
public class RequestForm implements Convertable<Request> {

    private static final ResourceBundle ERRORS;

    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");
    }

    @Inject
    protected HttpServletRequest request;

    protected @EJB
    UserDao dao;
    protected @EJB
    VacationTypeDao vacationTypeDao;
    protected @EJB
    RequestStateDao requestStateDao;

    protected String manager;
    protected String owner;
    protected String dateBegin;
    protected String dateEnd;
    protected String requestState;
    protected String vacationType;
    protected String ownerComment;
    protected String managerComment;
    protected Long id;

    public HttpServletRequest getRequest() {
        return request;
    }

    @PostConstruct
    public void onPostConstruct() {
        manager = request.getParameter("requestManager");
        owner = request.getParameter("requestOwner");
        dateBegin = request.getParameter("dateBegin");
        dateEnd = request.getParameter("dateEnd");
        vacationType = request.getParameter("vacationType");
        requestState = request.getParameter("requestState");
        ownerComment = request.getParameter("ownerComment");
        managerComment = request.getParameter("managerComment");
        
                
      if (!request.getParameter("id").equals("") )
                id = Long.parseLong(request.getParameter("id"));      
    }

     public Long getId() {
        return id;
    }
    public String getManager() {
        return manager;
    }

    public String getOwner() {
        return owner;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getRequestState() {
        return requestState;
    }

    public String getVacationType() {
        return vacationType;
    }

    private void validate() throws ValidationException {
        
        assertNotEmpty(manager);        
        assertNotEmpty(dateBegin);
        assertNotEmpty(dateEnd);
        assertNotEmpty(requestState);
        assertNotEmpty(vacationType);
        assertNotEmpty(owner);

        assertDate(dateBegin);
        assertDate(dateEnd);
    }

    @Override
    public Request convertTo(Class<Request> cls) throws ValidationException {
        validate();
        User managerConvert = (User) parseParameter(dao, "requestManager");
        User ownerConvert = (User) parseParameter(dao,   "requestOwner");
        java.sql.Date dateBeginConvert = getDate(dateBegin);
        java.sql.Date dateEndConvert = getDate(dateEnd);
        VacationType vacationTypeConvert = (VacationType) parseParameter(vacationTypeDao,  "vacationType");
        RequestState requestStateConvert = (RequestState) parseParameter(requestStateDao,  "requestState");

        return new Request(ownerConvert,
                managerConvert,
                dateBeginConvert,
                dateEndConvert,
                requestStateConvert,
                vacationTypeConvert,
                ownerComment,
                managerComment);
    }

    private Object parseParameter(AbstractDao dao,   String paramName) {
        String param = request.getParameter(paramName);
        if (param != null){
        Long id = Long.parseLong(param);
        return dao.find(id);
        }
        else{
            return null;
        }
    }

    private java.sql.Date getDate(String textDate) throws ValidationException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            java.util.Date utilDate = formatter.parse(textDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        } catch (IllegalArgumentException | ParseException e) {
            throw new ValidationException(ERRORS.getString("error.validation.wrong-date"));
        }
    }

}
