package dev.richard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.daos.UserDAO;
import dev.richard.entities.Password;
import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.utils.GenerationUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserDAO userDAO;
    public UserServlet(ObjectMapper m, UserDAO u) {
        mapper = m;
        userDAO = u;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(501);
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
            System.out.println(user);
            userDAO.createUser(user);

            resp.setStatus(204);

        } catch (Exception e) {
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

