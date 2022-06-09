package dev.richard.soulnotes.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.soulnotes.daos.EmailDAO;
import dev.richard.soulnotes.daos.EmailDaoPostgres;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.dtos.ErrorResponse;
import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.utils.GenerationUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private final UserDAO userDAO;
    private final EmailDAO emailDAO = new EmailDaoPostgres();
    public ResetServlet(ObjectMapper mapper, UserDAO userDAO) {
        this.objectMapper = mapper;
        this.userDAO = userDAO;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);

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
            return;
        }
        ObjectNode jsonNodes = objectMapper.createObjectNode();
        jsonNodes.put("userId", reset.getUserId());
        jsonNodes.put("token", reset.getResetToken());
        resp.sendRedirect("update.html");

        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(jsonNodes));
    }
}
