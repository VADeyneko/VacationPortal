 
package helpers;

import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Victor Deyneko <VADeyneko@gmail.com>
 */
@Named
@RequestScoped
public class MailService {
    @Resource(name = "vdsecondary")
    private Session mailSession;
    
    public void sendEmail(Message msg) throws MessagingException{
         Transport.send(msg);
    }
    
    public  Message createMessage(String subj, String contactName, String email, String text)
            throws MessagingException, UnsupportedEncodingException {
           
        Message msg = new MimeMessage(mailSession);
        msg.setSubject(subj);
        msg.setRecipient(Message.RecipientType.TO,
                         new InternetAddress(
                         email,
                         contactName)); 
        msg.setFrom(new InternetAddress(
                         "vd.secondary@gmail.com",
                         "Система оповещений портала"));
       
        msg.setContent(text, "text/html; charset=utf-8");
        
        return msg;
    }
         
  
}
