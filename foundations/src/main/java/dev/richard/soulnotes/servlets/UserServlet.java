package dev.richard.soulnotes.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.dtos.ErrorResponse;
import dev.richard.soulnotes.entities.Password;
import dev.richard.soulnotes.entities.Roles;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.exceptions.EmailAlreadyUsedException;
import dev.richard.soulnotes.services.UserService;
import dev.richard.soulnotes.utils.GenerationUtil;
import dev.richard.soulnotes.utils.LogLevel;
import dev.richard.soulnotes.utils.LoggerUtil;

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
    private ObjectNode nodes;
    private ErrorResponse error;
    private final UserDAO userDAO;
    private List<User> userList;
    private String logString;
    private final UserService userService;
    public UserServlet(ObjectMapper m, UserDAO u, UserService service) {
        mapper = m;
        userDAO = u;
        userService = service;
    }
    @Override
    // Code adapted from Jeremy Smalls-Bushay.
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userList = userDAO.getAllUsers();
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
        nodes = mapper.createObjectNode();
        if (userList.isEmpty()) {
            nodes.put("code", 400);
            nodes.put("message", "No users exist with provided info.");
            nodes.put("timestamp", LocalDateTime.now().toString());

            LoggerUtil.getInstance().log("No users exist with provided info.", LogLevel.ERROR);
            String result = mapper.writeValueAsString(nodes);
            resp.setContentType("application/json");
            resp.getWriter().write(result);
            return;
        }

        String result = mapper.writeValueAsString(userList);
        resp.setContentType("application/json");
        resp.getWriter().write(result);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = mapper.readValue(req.getInputStream(), User.class);
            /*user.setRoleId(1);
            user.setRoleType(Roles.BASIC);*/
            System.out.println("servlet: " + user);
            User potentialUser = userDAO.getUserByUsername(user.getUsername());
            if (potentialUser != null) {
                error = new ErrorResponse(409, "This username is already in use.");
                resp.setContentType("application/json");
                resp.setStatus(409);
                resp.getWriter().write(error.generateErrors(mapper));
                return;
            }
            potentialUser = userDAO.getUserByEmail(user.getEmail());
            if (potentialUser != null) {
                error = new ErrorResponse(409, "This email is already in use.");
                resp.setContentType("application/json");
                resp.setStatus(409);
                resp.getWriter().write(error.generateErrors(mapper));
                return;
            }
            userService.createUser(user);
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

