package forms;

import beans.UserGroupService;
import beans.UserService;
import dao.AbstractDao;
import dao.UserDao;
import dao.UserGroupDao;
 import forms.core.Convertable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import model.User;
import static forms.core.Validation.*;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.security.auth.login.AccountNotFoundException;
import model.Credentials;
import model.UserGroup;


@Named
@RequestScoped
public class UserForm implements Convertable<User> {

    private static final ResourceBundle ERRORS;
    
    static {
        ERRORS = ResourceBundle.getBundle("resources.errors");
    }
    
    @Inject
    protected HttpServletRequest request;
    
    @Inject
    protected UserGroupService userGroupService;
         
    @Inject
    protected UserService service;
    
    protected @EJB UserDao dao;
    protected @EJB UserGroupDao ug_dao;
    
    
    protected String name;
    
    protected String lastname;
    
    protected String email;
    
    protected String password;
    
    protected String confirmation;
    
   protected UserGroup userGroup;
   
   protected User manager;
    
   protected long id;
    
    @PostConstruct
    public void onPostConstruct() {
        name = request.getParameter("name");
        lastname = request.getParameter("lastname");
        email = request.getParameter("email");
        password = request.getParameter("password");
        confirmation = request.getParameter("password-confirmation");
       
        
      if (!request.getParameter("id").equals("") )
                id = Long.parseLong(request.getParameter("id"));      
        
//      if ( request.getParameter("userGroup") != null)
//          userGroup = userGroupService.findById(Integer.parseInt(request.getParameter("userGroup")));
//        
//      if ( request.getParameter("manager") != null)
//          manager =  service.findById(Long.parseLong(request.getParameter("manager")) );
//   
         userGroup = (UserGroup) parseParameter(ug_dao, "userGroup");
         manager = (User) parseParameter(dao, "manager");
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public long  getId() {
        return id;
    }

    public String getConfirmation() {
        return confirmation;
    }
    
    public void validate() throws ValidationException {
        assertNotEmpty(name);
        assertNotEmpty(lastname);
        assertNotEmpty(email);
        assertNotEmpty(password);
        assertNotEmpty(confirmation);
        assertEmail(email);
        assertNotEmpty(userGroup.getGroupLabelName());
        if (!password.equals(confirmation)) {
            throw new ValidationException(errorMessage("error.validation.password.not-match"));
        }    
          
    }
    
    @Override
    public User convertTo(Class<User> cls) throws ValidationException {
        validate();
        Credentials credentials = new Credentials(email, password);        
        User user = new User(credentials, name, lastname, userGroup  );
        if(manager != null)   user.setManager(manager);
        
        return user;   
    }
    
    // метод добавлен сюда, т.к. есть валидация
    public void validateAndUpdate(User user) throws AccountNotFoundException{
               validate();
               user.setName( name );
               user.setLastname(lastname);
               Credentials credentials = user.getCredentials();
               credentials.setPassword(password);              
               user.setCredentials(credentials);
               user.setUserGroup(userGroup);
               user.setManager(manager);
               
               service.update(user);
               service.singIn(credentials);
    }
   
    protected String errorMessage(String name) {
        return ERRORS.getString(name);
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

    
}