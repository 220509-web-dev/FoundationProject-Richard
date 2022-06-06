package dev.richard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.Password;
import dev.richard.entities.User;
import dev.richard.utils.LogLevel;
import dev.richard.utils.LoggerUtil;
import dev.richard.utils.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
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
            Password generatedPassword = PasswordUtil.generatePassword(providedPass, u.getSalt());
            System.out.printf("User hash of %s: %s\n", u.getUsername(), new String(u.getPasswordHash(), StandardCharsets.UTF_8));
            System.out.printf("Generated hash of %s: %s\n", providedUser, new String(generatedPassword.getHash(), StandardCharsets.UTF_8));
            System.out.println(providedUser.equals(u.getUsername()) && u.getPasswordHash().equals(generatedPassword.getHash()));
            if (providedUser.equals(u.getUsername()) && Arrays.equals(u.getPasswordHash(), generatedPassword.getHash())) {
                // LoggerUtil.log("Found user.", LogLevel.INFO);
                System.out.println("Found user.");

                resp.setStatus(200);
                resp.setContentType("application/json");

                ObjectNode jsonNodes = mapper.createObjectNode();
                jsonNodes.put("firstName", u.getFirstName());
                jsonNodes.put("lastName", u.getLastName());
                jsonNodes.put("username:", u.getUsername());

                resp.getWriter().write(mapper.writeValueAsString(jsonNodes));
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
