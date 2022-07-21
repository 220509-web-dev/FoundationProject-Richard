package dev.richard.soulnotes.servlets;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.soulnotes.daos.EmailDAO;
import dev.richard.soulnotes.daos.EmailDaoPostgres;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.dtos.ErrorResponse;
import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.entities.Password;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.utils.GenerationUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ResetServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private final UserDAO userDAO;
    private final EmailDAO emailDAO = new EmailDaoPostgres();
    private ErrorResponse error;
    public ResetServlet(ObjectMapper mapper, UserDAO userDAO) {
        this.objectMapper = mapper;
        this.userDAO = userDAO;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String,Object> in = objectMapper.readValue(req.getInputStream(), HashMap.class);
        EmailReset reset = emailDAO.getResetTokenByToken(String.valueOf(in.get("token")));
        String newPassword = String.valueOf(in.get("password"));

        Password p = GenerationUtil.generatePassword(newPassword);
        User u = userDAO.getUserById(reset.getUserId());

        u.setSalt(p.getSalt());
        u.setPasswordHash(p.getHash());

        userDAO.updateUser(u);
        emailDAO.deleteResetToken(reset);
        resp.setStatus(204);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = objectMapper.readValue(req.getInputStream(), String.class);
        EmailReset reset = emailDAO.getResetTokenByToken(token);
        if (LocalDateTime.now().isAfter(reset.getExpiration())) {
            error = new ErrorResponse(409, "This token has expired.");
            emailDAO.deleteResetToken(reset);
            resp.setContentType("application/json");
            resp.getWriter().write(error.generateErrors(objectMapper));
            resp.sendRedirect("/soulnotes");
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doGet(req, resp);
        String token = req.getParameter("token");
        EmailReset reset = emailDAO.getResetTokenByToken(token);
        if (reset == null) {
            ErrorResponse errorResponse = new ErrorResponse(409, "Invalid token.");
            resp.setContentType("application/json");
            resp.setStatus(409);
            resp.getWriter().write(errorResponse.generateErrors(objectMapper));
            resp.sendRedirect("/soulnotes");
            return;
        }
        resp.addHeader("token", reset.getResetToken());
        resp.addHeader("expiration", reset.getExpiration().toString());
        req.getRequestDispatcher("update.html").forward(req, resp);

    }
}
