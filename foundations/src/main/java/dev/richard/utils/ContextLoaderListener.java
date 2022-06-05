package dev.richard.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.User;
import dev.richard.servlets.AuthServlet;
import dev.richard.servlets.UserServlet;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.*;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectMapper mapper = new ObjectMapper();
        UserDAO userDAO = new UserDaoPostgres();
        UserServlet userServlet = new UserServlet(mapper);
        ServletContext context = sce.getServletContext();

        ServletRegistration.Dynamic registration = context.addServlet("UserServlet", userServlet);
        registration.addMapping("/users/*");

        context.addServlet("AuthServlet", new AuthServlet(mapper, userDAO)).addMapping("/auth");
    }
}
