package beans;

import dao.AppSettingsDao;
import dao.RequestDao;
import dao.RequestStateDao;
import exceptions.PrimaryKeyViolationException;
import helpers.DateFormatter;
import helpers.MailService;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.validation.ValidationException;
import model.AppSettings;
import model.Request;
import model.RequestState;
import model.User;

@Named
@RequestScoped
public class RequestService implements Serializable {

    private static final ResourceBundle ERRORS, LABELS;

    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");
        LABELS = ResourceBundle.getBundle("resources.labels");
    }

    protected @EJB
    RequestDao dao;
    
    protected @EJB
    AppSettingsDao appSettingsDao;

    protected @EJB
    RequestStateDao requestStateDao;

    @Inject
    protected MailService mailService;

    public void register(Request rqst) throws PrimaryKeyViolationException {
        dao.create(rqst);
    }

    public Request findById(Long id) {
        return dao.find(id);
    }

    public void delete(Request request) {
        dao.delete(request);
    }

    public void update(Request request) {
        dao.update(request);
    }

    public RequestState getRequestState(String id) {
        RequestState result;
        result = requestStateDao.find(Long.parseLong(id));
        return result;
    }

    public void checkIntersectionByOwner(Request req, String action) {
        List<Request> lst = dao.findOwnerIntersections(req.getDateBegin(), req.getDateEnd(), req.getOwner());

        if (!action.contains("Insert")) {
            if (lst.size() > 1) //count itself
            {
                throw new ValidationException(ERRORS.getString("error.request-intersection-by-owner-found")
                        + " " + lst.get(1).getFormatedDateBegin() + "  -  " + lst.get(1).getFormatedDateEnd()
                );
            }
        } else {
            if (lst.size() > 0) //insert new
            {
                throw new ValidationException(ERRORS.getString("error.request-intersection-by-owner-found")
                        + " " + lst.get(0).getFormatedDateBegin() + "  -  " + lst.get(0).getFormatedDateEnd()
                );
            }
        } //end-if-else       
    }

    public void validateAgainstAppSettings(Request request) throws ValidationException{
        AppSettings settings  = appSettingsDao.getSettingsInstance();
        int days =  DateFormatter.daysBetween(request.getDateBegin(), request.getDateEnd());
        if (days > settings.getMaxDaysAllowed())
            throw new ValidationException (ERRORS.getString("error.such-long-period-is-not-allowed"));
        if (days < settings.getMinDaysAllowed())
           throw new ValidationException (ERRORS.getString("error.such-short-period-is-not-allowed"));
        
        if(request.getDateBegin().before(settings.getdPeriodBegin()))
              throw new ValidationException (ERRORS.getString("error.the-request-lies-out-of-allowed-period"));
        
        if(request.getDateEnd().after(settings.getdPeriodEnd()))
              throw new ValidationException (ERRORS.getString("error.the-request-lies-out-of-allowed-period"));

    }
    
    /*
   * Method modifies the Request object with the new state is called from RequestServlet   
     */
    public void forwardToState(Request request, RequestState newState, String managerComment) throws ValidationException, MessagingException {
        if ((request.getRequestState().getId() == 2 && newState.getId() == 4) && managerComment.trim().isEmpty()) {
            throw new ValidationException(ERRORS.getString("error.validation.manager-comment-required"));
        }

        if (!request.getRequestState().getId().equals(newState.getId())) {
            request.addCloneToHistory(request); //store itself for history
        }

        request.setManagerComment(managerComment);
        request.setRequestState(newState);   //set the new State      
        
    }

    /*
    *method takes the request, and determines whom to notify depending on state (RequestState.isManagerState). sends the message
    */
    public void notifyUser(Request request, String link) throws MessagingException, UnsupportedEncodingException{
        String subj = request.getRequestState().getMessageSubject();
        String body =      getMailMessage(request, link);     
        User usr = request.getRequestState().getIsManagerState() ? request.getOwner() :   request.getManager() ;// In manager state we write the owner!
        String email = usr.getCredentials().getEmail();
        email = "vadeyneko@gmail.com"; //for tests
                
        Message msg = mailService.createMessage(subj, request.getManager().getFullName(), email, body );
        
        Logger.getLogger("beans.RequestService").log(Level.INFO,   "email"+email + " text: " + body);           
        mailService.sendEmail(msg);
    }
    
    
   private String getMailMessage(Request request, String link)   {
       
        String text = request.getRequestState().getMessageBody();
       
        StringBuilder body = new StringBuilder(LABELS.getString("label.dear")).append(request.getManager().getFullName()).append("!");
        body.append(".").append("<p>").append(text).append("</p>");
        body.append(LABELS.getString("label.link"));
        link = link.replace("requestForward", "requestDetails");
        
        body.append("<a href=\"").append(link).append("\">").append(link).append("</a>");
        body.append("<p>").append("</p>") ;
        body.append("<p>").append(LABELS.getString("label.thanks")).append("</p>") ;
        
        return body.toString();
        
   }
    
    
}
