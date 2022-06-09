package dev.richard.soulnotes.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.dtos.ErrorResponse;
import dev.richard.soulnotes.entities.Password;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.utils.GenerationUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AuthServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserDAO userDAO;

    public AuthServlet(ObjectMapper m, UserDAO u) {
        this.mapper = m;
        this.userDAO = u;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        resp.setStatus(204);    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        HashMap<String, Object> credentials = mapper.readValue(req.getInputStream(), HashMap.class);
        String providedUser = (String) credentials.get("username");
        String providedPass = (String) credentials.get("password");
        User u = userDAO.getUserByUsername(providedUser);

        if (u == null) {
            ErrorResponse e = new ErrorResponse(409, "No user found with provided credentials.");
            resp.setStatus(409);
            resp.setContentType("application/json");
            resp.getWriter().write(e.generateErrors(mapper));
            return;
        }
        Password generatedPassword = GenerationUtil.generatePassword(providedPass, u.getSalt());
        if (Arrays.equals(u.getPasswordHash(), generatedPassword.getHash())) {
            // LoggerUtil.getInstance().log("Found user.", LogLevel.INFO);
            System.out.println("Found user.");

            resp.setStatus(200);
            resp.setContentType("application/json");

            ObjectNode jsonNodes = mapper.createObjectNode();
            jsonNodes.put("firstName", u.getFirstName());
            jsonNodes.put("lastName", u.getLastName());
            jsonNodes.put("username:", u.getUsername());
            HttpSession session = req.getSession();
            session.setAttribute("auth-user", u);
            resp.getWriter().write(mapper.writeValueAsString(u));
            return;
        }
        resp.setStatus(409);
        resp.setContentType("application/json");
        resp.getWriter().write(new ErrorResponse(409, "Invalid credentials.").generateErrors(mapper));
    }
}
