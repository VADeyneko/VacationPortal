 
package beans;

import dao.UserGroupDao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import model.MenuItem;
import model.User;
import model.UserGroup;

@Named
@SessionScoped
public class UserGroupService  implements Serializable{
 
 
   protected @EJB UserGroupDao dao;
     
  public UserGroup findById(int id)   {
      return dao.find(id);
  }
     
 
    
}
