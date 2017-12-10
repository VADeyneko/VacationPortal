package forms;

import beans.RequestService;
import dao.AbstractDao;
import dao.RequestStateDao;
import dao.UserDao;
import dao.VacationTypeDao;
import forms.core.Convertable;
import forms.core.FormParamProps;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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
    UserDao userDao;
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
    
    private FormParamProps paramProps;

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

        if ((request.getParameter("id")!=null ) && !request.getParameter("id").equals("")  ){
            id = Long.parseLong(request.getParameter("id"));
        }    
    }

    public Long getId() {
        return id;
    }

    public String getManager() {
        return manager;
    }
    
    
    public Long getManagerId() {
        try{
        return Long.parseLong(manager);
        }
        catch (NumberFormatException ee){
             throw new ValidationException(ERRORS.getString("error.validation.wrong-number"));
        }
    }

    public String getOwner() {
        return owner;
    }
    
    public Long getOwnerId() {
        try{
        return Long.parseLong(owner);
        }
        catch (NumberFormatException ee){
             throw new ValidationException(ERRORS.getString("error.validation.wrong-number"));
        }
    }

    public String getDateBegin() {
        return dateBegin;
    }
    
    public  java.sql.Date getValidatedDateBegin(){
         return  getDate(dateBegin);
    }

    public String getDateEnd() {
        return dateEnd;
    }
    
    public  java.sql.Date getValidatedDateEnd(){
         return  getDate(dateEnd);
    }

    public String getRequestState() {
        return requestState;
    }
    
     public Long getRequestStateId() {
        try{
        return Long.parseLong(requestState);
        }
        catch (NumberFormatException ee){
             throw new ValidationException(ERRORS.getString("error.validation.wrong-number"));
        }
    }

    public String getVacationType() {
        return vacationType;
    }

    public Long getVacationTypeId() {
        try{
        return Long.parseLong(vacationType);
        }
        catch (NumberFormatException ee){
             throw new ValidationException(ERRORS.getString("error.validation.wrong-number"));
        }
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
        User managerConvert = (User) parseParameter(userDao, "requestManager");
        User ownerConvert = (User) parseParameter(userDao, "requestOwner");
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
    
  
    public  void fillVacationTypeDropdown(){
       List<VacationType> vacationTypeList = vacationTypeDao.all();
       request.setAttribute("vacationTypeList", vacationTypeList);        
    }
    
   public  void fillManagerDropdown(){
        List<User> managerList   = userDao.all();
        request.setAttribute("managerList", managerList);
    }

      public  void fillOwnerDropdown(User owner){
        List<User> ownerList = new ArrayList<>();
        ownerList.add(owner); //Без вариантов только один создатель
        request.setAttribute("ownerList", ownerList);
    }
      
    public  void fillOwnerDropdown(){
        List<User> ownerList = userDao.all();     
        request.setAttribute("ownerList", ownerList);
    }  
      
     public  void fillRequestStateDropdown(Request objToEdit, boolean byManager){
         LinkedList<RequestState> requestStateList = new LinkedList<>();
       
         if(objToEdit != null){
            requestStateList.add(objToEdit.getRequestState());
            requestStateList.addAll(objToEdit.getRequestState().getPossibleStates(byManager));
            request.setAttribute("requestStateList", requestStateList);
        } else {
            requestStateList.addAll(requestStateDao.getInitialStateList());
            request.setAttribute("requestStateList", requestStateList);
         }
         
       // вспомогательный блок для определения в ReqeustForm.tag того,
        //какой элемент requestStateList выбран и Endabled
        if (request.getParameter("toState") != null) {
            request.setAttribute("selectedState_Id", request.getParameter("toState"));
        } else {
             try {
                   request.setAttribute("selectedState_Id", requestStateList.getFirst().getId());
                 } catch (NoSuchElementException ignore) {}
           
        }
    }     
     
     public  void fillRequestStateDropdown(){
         List<RequestState> requestStateList = new LinkedList<>();
         requestStateList.addAll(requestStateDao.all());
         request.setAttribute("requestStateList", requestStateList);   
         request.setAttribute("selectedState_Id", requestStateList.get(0).getId());
    }      
     

    
    public FormParamProps initParamProps(){
        if (paramProps == null){
        paramProps = new FormParamProps();
        paramProps.add("requestManager");
        paramProps.add("requestOwner");
        paramProps.add("dateBegin");
        paramProps.add("dateEnd");
        paramProps.add("vacationType");
        paramProps.add("requestState");
        paramProps.add("ownerComment");
        paramProps.add("managerComment");
         paramProps.add("date-range0-container");
        
        }
        
        return paramProps;
    }
    
}
