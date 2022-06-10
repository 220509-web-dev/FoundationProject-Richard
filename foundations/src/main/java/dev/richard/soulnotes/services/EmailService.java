package dev.richard.soulnotes.services;

import com.sun.mail.smtp.SMTPProvider;
import dev.richard.soulnotes.daos.EmailDAO;
import dev.richard.soulnotes.daos.EmailDaoPostgres;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.daos.UserDaoPostgres;
import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.exceptions.UserNotFoundException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;

public class EmailService {
    private static UserDAO userDAO = new UserDaoPostgres();
    private static EmailDAO emailDAO = new EmailDaoPostgres();
    public static void sendResetEmail(String email) throws IOException {
        Properties props = new Properties();
        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));

        Properties emailProps = new Properties();
        emailProps.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
        emailProps.put("mail.smtp.starttls.enable", props.getProperty("mail.smtp.starttls.enable"));
        emailProps.put("mail.smtp.host", props.getProperty("mail.smtp.host"));
        emailProps.put("mail.smtp.port", props.getProperty("mail.smtp.port"));
        // System.out.println(emailProps);

        String to = email;
        String from = "noreply@soulnotes.xyz";
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return;
        }
        EmailReset reset = emailDAO.addReset(user);
        String link = String.format("localhost:8080/soulnotes/reset?token=%s", reset.getResetToken());
        Session session = Session.getInstance(emailProps,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("email-username"), props.getProperty("email-password"));
                    }
                });
        session.addProvider(new SMTPProvider());
        String body = String.format("Hey there, %s! We received a request to reset your password."
                + "If this was you, please follow the link here: %s. \nOtherwise, feel free to ignore this email." +
                "\nThis link expires in ten minutes.", user.getFirstName(), link);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "noreply"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Test");
            message.setText(body);
            Transport.send(message);
            System.out.println("Sent!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
