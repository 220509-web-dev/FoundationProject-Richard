package dev.richard.app;

import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.utils.ConnectionUtil;
import dev.richard.utils.LogLevel;
import dev.richard.utils.LoggerUtil;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Connection c = ConnectionUtil.getConnection();
        UserDAO userDAO = new UserDaoPostgres();
        List<User> userList = userDAO.getAllUsers();
        System.out.println(userList);
    }
}
