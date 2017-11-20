package forms;

 
import forms.core.Convertable;
import javax.servlet.http.HttpServletRequest;
import model.Credentials;
import static forms.core.Validation.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;

@Named
@RequestScoped
public class LoginForm implements Convertable<Credentials> {

    @Inject 
    protected HttpServletRequest request;
    

    
    @Override
    public Credentials convertTo(Class<Credentials> cls) throws ValidationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        assertNotEmpty(email);
        assertNotEmpty(password);
        assertEmail(email);
        
        return new Credentials(email, password);
    }
    
}
