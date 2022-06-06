package dev.richard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.Password;
import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.exceptions.UserNotFoundException;
import dev.richard.utils.GenerationUtil;
import dev.richard.utils.LogLevel;
import dev.richard.utils.LoggerUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserDAO userDAO;
    private List<User> userList;
    private String logString;
    public UserServlet(ObjectMapper m, UserDAO u) {
        mapper = m;
        userDAO = u;
        userList = u.getAllUsers();
    }
    @Override
    // Code adapted from Jeremy Smalls-Bushay.
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        //Get user by Username
        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            userList = userList.stream().filter(user -> user.getUserId() == userId).collect(Collectors.toList());

        } catch (NumberFormatException e) {
            logString = "Null or invalid input.";
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
        }

        // filter userList based on username
        if (username != null) {
            userList = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        }
        ObjectNode nodes = mapper.createObjectNode();
        if (userList.isEmpty()) {
            nodes.put("code", 400);
            nodes.put("message", "No users exist with provided info.");
            nodes.put("timestamp", LocalDateTime.now().toString());

            String result = mapper.writeValueAsString(nodes);
            resp.setContentType("application/json");
            resp.getWriter().write(result);
        }
        User foundUser = userList.get(0);
        // set response
        nodes.put("userId", foundUser.getUserId());
        nodes.put("firstName", foundUser.getFirstName());
        nodes.put("lastName", foundUser.getLastName());
        nodes.put("username", foundUser.getUsername());
        nodes.put("role", foundUser.getRoleType().name());

        String result = mapper.writeValueAsString(nodes);
        resp.setContentType("application/json");
        resp.getWriter().write(result);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = mapper.readValue(req.getInputStream(), User.class);
            Password password = GenerationUtil.generatePassword(user.getPassword());
            user.setPasswordHash(password.getHash());
            user.setSalt(password.getSalt());
            user.setRoleId(1);
            user.setRoleType(Roles.BASIC);
            System.out.println("servlet: " + user);
            userDAO.createUser(user);
            System.out.println("user created!");

            resp.setStatus(204);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
