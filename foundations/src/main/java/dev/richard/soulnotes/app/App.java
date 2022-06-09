package dev.richard.soulnotes.app;

import com.sun.mail.smtp.SMTPProvider;
import dev.richard.soulnotes.daos.UserDaoPostgres;
import dev.richard.soulnotes.utils.GenerationUtil;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class App {
    public static void main(String[] args) throws Exception {
        String to = "richard@richardmoch.xyz";
        String from = "fake@email.lol";

        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));

        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
        emailProps.put("mail.smtp.starttls.enable", props.getProperty("mail.smtp.starttls.enable"));
        emailProps.put("mail.smtp.host", props.getProperty("mail.smtp.host"));
        emailProps.put("mail.smtp.port", props.getProperty("mail.smtp.port"));
        System.out.println(emailProps);
        Session session = Session.getInstance(emailProps,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("email-username"), props.getProperty("email-password"));
                    }
                });
        session.addProvider(new SMTPProvider());
        String body = "Test email.";

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "noreply"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Test");
            message.setText(body);
            Provider p = new SMTPProvider();
            Transport.send(message);
            System.out.println("Sent!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
