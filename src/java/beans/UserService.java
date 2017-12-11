package beans;

//import dao.MenuItemDao;
import dao.MenuItemDao;
import dao.UserDao;
import exceptions.PrimaryKeyViolationException;
import helpers.PasswordEncrypter;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
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
    
    @Inject
    private PasswordEncrypter encrypter;
    
    private Collection<MenuItem> allowedMenuItems;
    
    private static final ResourceBundle ERRORS;

    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");         
    }

    public void register(User user) throws PrimaryKeyViolationException, NoSuchAlgorithmException {
       
        if (dao.exists(user.getCredentials())) {
            throw new PrimaryKeyViolationException( ERRORS.getString("error.user-already-exists"));
        }
        
        encryptUserCredentials(user);                
        dao.create(user);
 
    }
    
    private  void encryptUserCredentials(User user) throws NoSuchAlgorithmException{  
        Credentials credentials = user.getCredentials();      
        String password = credentials.getPassword();
        password = encrypter.encrypt(password);
        credentials.setPassword(password);
        user.setCredentials(credentials);
    }
    
    private void authenticate(Credentials credentials) throws AccountNotFoundException{
          if (dao.exists(credentials)) {
                try{
                 User user = dao.getExistingUser(credentials);
                 String password = encrypter.encrypt(credentials.getPassword());
                 
                 if(user.getCredentials().getPassword().equals(password)) 
                      return;
                } catch (NoSuchAlgorithmException e){
                    throw new AccountNotFoundException();
                }
           }
          throw new AccountNotFoundException(ERRORS.getString("error.account.user-not-found"));
    }

    public void singIn(Credentials credentials) throws AccountNotFoundException {
        if (dao.exists(credentials)) {
            authenticate(credentials);
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
