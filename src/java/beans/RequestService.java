 
package beans;
 
import dao.RequestDao;
import dao.RequestStateDao;
import exceptions.PrimaryKeyViolationException;
import java.io.Serializable;
 
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.ValidationException;
import model.Request;
import model.RequestState;


@Named
@RequestScoped
public class RequestService  implements Serializable{       
    
     private static final ResourceBundle ERRORS;

    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");         
    }

    
    protected @EJB RequestDao dao;
    
    protected @EJB RequestStateDao requestStateDao;
    
    public void register(Request rqst) throws PrimaryKeyViolationException {        
          dao.create(rqst);
    }
       
    public Request findById (Long id){
        return dao.find(id);
    }
    
    public void delete(Request request) {
        dao.delete(request);
    }
    
    public void update (Request request) {        
        dao.update(request);
    }

   public RequestState getRequestState(String id)  {
        RequestState result ;           
        result = requestStateDao.find( Long.parseLong( id) );        
        return result;
   }
   
   public void checkIntersectionByOwner (Request req , String action) {
        List<Request> lst = dao.findOwnerIntersections(req.getDateBegin(), req.getDateEnd(), req.getOwner());
         
      
        if( !action.contains("Insert")){
             if(lst.size() > 1 ) //сам себя 
                    throw new ValidationException( ERRORS.getString("error.request-intersection-by-owner-found") +
                           " "+ lst.get(1).getFormatedDateBegin() +"  -  "  +  lst.get(1).getFormatedDateEnd() 
                    );
        } else {
             if(lst.size() > 0 ) //insert new
                    throw new ValidationException( ERRORS.getString("error.request-intersection-by-owner-found") +
                           " "+ lst.get(0).getFormatedDateBegin() +"  -  "  +  lst.get(0).getFormatedDateEnd() 
                    );
        } //end-if-else       
   } 

   
}
