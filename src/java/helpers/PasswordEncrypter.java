 package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *  
 * @author Victor Deyneko <VADeyneko@gmail.com>
 * provides MD5 hasging service for {@code Credentials}
 */
 
@Named
@RequestScoped
public class PasswordEncrypter {
    private   MessageDigest digest;


    public  String encrypt( String source) throws NoSuchAlgorithmException{         
        this.digest = MessageDigest.getInstance("MD5");
        return new String (digest.digest(source.getBytes()));
    }
    
        
     
    
}
