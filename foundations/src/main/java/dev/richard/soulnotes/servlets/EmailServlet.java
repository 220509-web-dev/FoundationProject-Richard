package dev.richard.soulnotes.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.richard.soulnotes.daos.EmailDAO;
import dev.richard.soulnotes.daos.EmailDaoPostgres;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.services.EmailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EmailServlet extends HttpServlet {
    private ObjectMapper mapper;
    private final UserDAO userDAO;
    private final EmailDAO emailDAO;
    public EmailServlet(ObjectMapper mapper, UserDAO userDAO) {
        this.mapper = mapper;
        this.userDAO = userDAO;
        this.emailDAO = new EmailDaoPostgres();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = mapper.readValue(req.getInputStream(), String.class);
        User user = userDAO.getUserByEmail(email);
        if (user == null) return;
        EmailService.sendResetEmail(email);
    }
}
