package beans;

import dao.UserDao;
import exceptions.PrimaryKeyViolationException;
import java.io.Serializable;
import java.util.ArrayList;
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

            List<MenuItem> MenuItemList = new ArrayList<>();
            MenuItemList.addAll(user.getUserGroup().getMenuItems());

            session.setAttribute("sessionMenuItems", MenuItemList);

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
        this.user = user;
        
    }

    public void delete(User user) {
        dao.delete(user);
    }
}
