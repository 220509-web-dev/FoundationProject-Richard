package dev.richard.validation;

import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.User;
import dev.richard.exceptions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserValidation {
    private UserDAO userDAO;

    public void validateCredentials(String username, String password) {
        userDAO = new UserDaoPostgres();
        User u = userDAO.getUserByUsername(username);

        if (u == null) throw new UserNotFoundException();
        if (password != u.getPassword()) throw new InvalidCredentialsException();
    }

    public void checkEmail(String email) {
        userDAO = new UserDaoPostgres();

    }
}
