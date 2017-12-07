package forms;

import beans.RequestService;
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
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final ResourceBundle ERRORS, LABELS;

    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");
        LABELS = ResourceBundle.getBundle("resources.labels");
    }

    @Inject
    protected HttpServletRequest request;

    @Inject
    protected RequestService service;

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

        if (!request.getParameter("id").equals("")) {
            id = Long.parseLong(request.getParameter("id"));
        }

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

    public  Request validateAndUpdate(Request request) throws ValidationException {
        Request reqConv = convertTo(Request.class);
        if(!request.getRequestState().getId().equals( reqConv.getRequestState().getId() ) ) {
            request.addCloneToHistory(request); //запоминаем клон самого себя до изменений
        }            
        request.updateWithRequest(reqConv);
        reqConv = null;// обнуляем ссылку для GC
        return request ;
    }
    
   public Request forwardToState(Request request) throws ValidationException {
     assertNotEmpty(requestState);  
     RequestState newState = (RequestState) parseParameter(requestStateDao, "requestState");
     
     if(request.getRequestState().getId()== 2 && newState.getId() == 4 )
         assertNotEmpty(managerComment);  
     
     if(!request.getRequestState().getId().equals( newState.getId()) ) {
            request.addCloneToHistory(request); //запоминаем клон самого себя до изменений
        }
     
      request.setManagerComment(managerComment);
      request.setRequestState(newState);   //устанавливаем НОВОЕ состояние
      
      return request;
    }

    @Override
    public Request convertTo(Class<Request> cls) throws ValidationException {

        validate();
        User managerConvert = (User) parseParameter(dao, "requestManager");
        User ownerConvert = (User) parseParameter(dao, "requestOwner");
        java.sql.Date dateBeginConvert = getDate(dateBegin);
        java.sql.Date dateEndConvert = getDate(dateEnd);
        VacationType vacationTypeConvert = (VacationType) parseParameter(vacationTypeDao, "vacationType");
        RequestState requestStateConvert = (RequestState) parseParameter(requestStateDao, "requestState");
                

        return new Request(ownerConvert,
                managerConvert,
                dateBeginConvert,
                dateEndConvert,
                requestStateConvert,
                vacationTypeConvert,
                ownerComment,
                managerComment);
    }

    public LinkedHashMap<String, String> getDetailSummary(Request obj) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(LABELS.getString("label.requestOwner_header"), obj.getOwner().getFullName());
        map.put(LABELS.getString("label.requestManager_header"), obj.getManager().getFullName());
        map.put(LABELS.getString("label.requestDateBegin_header"), obj.getDateBegin().toString());
        map.put(LABELS.getString("label.requestDateEnd_header"), obj.getDateEnd().toString());
        map.put(LABELS.getString("label.requestState_header"), obj.getRequestState().getName());
        map.put(LABELS.getString("label.vacationType_header"), obj.getVacationType().getName());
        map.put(LABELS.getString("label.ownerComment_header"), obj.getOwnerComment());
        map.put(LABELS.getString("label.managerComment_header"), obj.getManagerComment());

        return map;
    }

    private Object parseParameter(AbstractDao dao, String paramName) {
        String param = request.getParameter(paramName);
        if (param != null) {
            Long id = Long.parseLong(param);
            return dao.find(id);
        } else {
            return null;
        }
    }

    private java.sql.Date getDate(String textDate) throws ValidationException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        formatter.applyPattern("dd.MM.yyyy");

        try {

            java.util.Date utilDate = formatter.parse(textDate);

            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            return sqlDate;
        } catch (IllegalArgumentException | ParseException e) {
            throw new ValidationException(ERRORS.getString("error.validation.wrong-date"));
        }

    }

}
