package beans;

//import dao.MenuItemDao;
import dao.MenuItemDao;
import dao.UserDao;
import exceptions.PrimaryKeyViolationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import model.Credentials;
import model.MenuItem;
import model.User;

@Named
@SessionScoped
public class UserService implements Serializable {

    @Inject
    private HttpSession session;

    private boolean isAuth;
    private User user;

    protected @EJB
    UserDao dao;
    
    protected @EJB
    MenuItemDao menuItemDao;
    
    private Collection<MenuItem> allowedMenuItems;

    public void register(User user) throws PrimaryKeyViolationException {
        if (dao.exists(user.getCredentials())) {
            throw new PrimaryKeyViolationException("error!");
        }
        dao.create(user);

    }

    public void singIn(Credentials credentials) throws AccountNotFoundException {
        if (dao.exists(credentials)) {
            isAuth = true;
            user = dao.getExistingUser(credentials);
            
            session.setAttribute("sessionUser", user); // устанавливаем юзера на всю сессию

            allowedMenuItems = new ArrayList<>(); 
            allowedMenuItems.addAll(user.getUserGroup().getMenuItems());
            session.setAttribute("sessionMenuItems", allowedMenuItems);
        } else {
            throw new AccountNotFoundException();
        }
    }

    public void signOut() {
        session.invalidate();
    }

    public boolean isAuth() {
        return isAuth;
    }
    
    public User getAuthUser() {
        return user;
    }

    public List<User> findAll() {
        return dao.all();
    }

    public User findById(long id) {
        return dao.find(id);
    }

    public void update(User user) {
        dao.update(user);
       if(this.user.getCredentials() == user.getCredentials())
                 this.user = user;        
    }

    public void delete(User user) {
        dao.delete(user);
    }
    
    /*
    * checks if user has access to menu item. used in filter
    */
    public boolean hasAccessToMenuItem(String menuItemPath){
        if (!isAuth)  
            return false;     
        
           Collection<MenuItem> dissallowed = new ArrayList<>();
           for (MenuItem i : menuItemDao.all() ){
                 if (!allowedMenuItems.contains(i))
                           dissallowed.add(i);
          }
          
           for (MenuItem i : dissallowed  ){
                if(menuItemPath.trim().equals(("/" +  i.getMenuPath().trim()))  )
                                    return false;
           }
           
            /*if the path is not of a menu, then return true*/             
           return true;
    }
      
}
