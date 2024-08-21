package mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    private static final String ENCODING = "UTF-8";
    private static final String SMTPHOST = "smtp.gmail.com";

    public Session setupSession(Properties props, String userName, String password) {
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTPHOST); 
        props.put("mail.smtp.port", "587"); // Port for STARTTLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Ensure TLSv1.2 is used

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
    }

    public void sendMail(Session session, String title, String content, String email) {
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("your-email@gmail.com", "moobee", ENCODING));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject(title);
            msg.setContent(content, "text/html; charset=utf-8");

            Transport.send(msg);
            System.out.println("메일 보내기 성공");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("메일 보내기 실패");
        }
    }
}
