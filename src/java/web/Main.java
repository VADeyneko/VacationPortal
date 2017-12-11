
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jms.Session;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.InitialContext;

public class Main {

    public void runTest() throws Exception {
        InitialContext ctx = new InitialContext();
        Session session =
            (Session) ctx.lookup("mail/vdsecondary");
        // Or by injection.
        //@Resource(name = "mail/<name>")
        //private Session session;

        // Create email and headers.
        Message msg = new MimeMessage((MimeMessage) session);
        msg.setSubject("My Subject");
        msg.setRecipient(RecipientType.TO,
                         new InternetAddress(
                         "vdeyneko@global-system.ru",
                         "Victor"));
        msg.setRecipient(RecipientType.CC,
                         new InternetAddress(
                         "ForVacancies2005@yandex.ru",
                         "Ou"));
        msg.setFrom(new InternetAddress(
                    "vd.secondary@gmail.com",
                    "VD"));

        // Body text.
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Here are the files.");
//
//        // Multipart message.
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//
//        // Attachment file from string.
//        messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setFileName("README1.txt");
//        messageBodyPart.setContent(new String(
//                                   "file 1 content"),
//                                   "text/plain");
//        multipart.addBodyPart(messageBodyPart);
//
//        // Attachment file from file.
//        messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setFileName("README2.txt");
//        DataSource src = new FileDataSource("file.txt");
//        messageBodyPart.setDataHandler(new DataHandler(src));
//        multipart.addBodyPart(messageBodyPart);
//
//        // Attachment file from byte array.
//        messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setFileName("README3.txt");
//        src = new ByteArrayDataSource(
//            "file 3 content".getBytes(),
//            "text/plain");
//        messageBodyPart.setDataHandler(new DataHandler(src));
//        multipart.addBodyPart(messageBodyPart);
//
//        // Add multipart message to email.
//        msg.setContent(multipart);

        // Send email.
        System.out.println(msg.getFrom().toString());
        Transport.send(msg);
    }

    public static void main(String[] args) {
        Main cli = new Main();
        try {
            cli.runTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}