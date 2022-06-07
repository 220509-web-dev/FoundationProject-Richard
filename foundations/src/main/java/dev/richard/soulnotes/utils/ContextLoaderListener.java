package dev.richard.soulnotes.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.daos.UserDaoPostgres;
import dev.richard.soulnotes.servlets.AuthServlet;
import dev.richard.soulnotes.servlets.UserServlet;

import javax.servlet.*;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectMapper mapper = new ObjectMapper();
        UserDAO userDAO = new UserDaoPostgres();
        UserServlet userServlet = new UserServlet(mapper, userDAO);
        ServletContext context = sce.getServletContext();

        ServletRegistration.Dynamic registration = context.addServlet("UserServlet", userServlet);
        registration.addMapping("/users/*");

        context.addServlet("AuthServlet", new AuthServlet(mapper, userDAO)).addMapping("/auth");
    }
}
