package dev.richard.soulnotes.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.soulnotes.daos.UserDAO;
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
        super.doDelete(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        List<User> users = userDAO.getAllUsers();
        HashMap<String, Object> credentials = mapper.readValue(req.getInputStream(), HashMap.class);
        String providedUser = (String) credentials.get("username");
        String providedPass = (String) credentials.get("password");

        for (User u : users) {
            Password generatedPassword = GenerationUtil.generatePassword(providedPass, u.getSalt());
            if (providedUser.equals(u.getUsername()) && Arrays.equals(u.getPasswordHash(), generatedPassword.getHash())) {
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
        }
        resp.setStatus(400);
        resp.setContentType("application/json");

        HashMap<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("code", 400);
        errorMessage.put("message", "No user found with provided credentials");
        errorMessage.put("timestamp", LocalDateTime.now().toString());

        resp.getWriter().write(mapper.writeValueAsString(errorMessage));
    }
}
