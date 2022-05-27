package dev.richard.app;

import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.utils.ConnectionUtil;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Connection c = ConnectionUtil.getConnection();
        UserDAO userDAO = new UserDaoPostgres();


        System.out.println(userDAO.getAllUsers());
    }
}
