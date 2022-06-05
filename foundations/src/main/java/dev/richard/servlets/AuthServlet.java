package dev.richard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.richard.daos.UserDAO;
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
        System.out.println(users.isEmpty());
        HashMap<String, Object> credentials = mapper.readValue(req.getInputStream(), HashMap.class);
        String providedUser = (String) credentials.get("username");
        String providedPass = (String) credentials.get("password");

        for (User u : users) {
            Password generatedPassword = PasswordUtil.generatePassword(providedPass, u.getSalt());
            if (providedUser.equals(u.getUsername()) && generatedPassword.equals(u.getPasswordHash())) {
                // LoggerUtil.log("Found user.", LogLevel.INFO);
                System.out.println("Found user.");

                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(String.valueOf(u));
                return;
            }
        }
    }
}
