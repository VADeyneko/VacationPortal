 
package beans;
 
import dao.RequestDao;
import exceptions.PrimaryKeyViolationException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import model.Request;

@Named
@RequestScoped
public class RequestService  implements Serializable{
    
    
    
    protected @EJB RequestDao dao;
    
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
       
}
