package dev.richard.app;

import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.utils.ConnectionUtil;

import java.sql.Connection;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Connection c = ConnectionUtil.getConnection();
        UserDAO userDAO = new UserDaoPostgres();
        User u = new User(0, "Test", "Test", "test", "test", "test", Roles.BASIC);
        System.out.println(u.getRoleId());
    }
}
