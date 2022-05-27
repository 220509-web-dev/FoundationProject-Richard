package dev.richard.app;

import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.User;
import dev.richard.utils.ConnectionUtil;

import java.sql.Connection;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Connection c = ConnectionUtil.getConnection();
        UserDAO userDAO = new UserDaoPostgres();

/*      Used for testing user creation -- SUCCEEDED
        System.out.printf("Last user before: %s\n", userDAO.getAllUsers().get(userDAO.getAllUsers().size() - 1));
        User user = new User(0, "Aweriusz", "Dogwoski", "bark@avery.dog", "adalbert", "testing", 1);
        userDAO.createUser(user);

        System.out.printf("Last user after: %s\n", userDAO.getAllUsers().get(userDAO.getAllUsers().size() - 1));
*/

/*      Used for testing user modification -- SUCCEEDED
        User user = userDAO.getUserById(3); // get avery
        System.out.printf("Old username is %s, ", user.getUsername());
        user.setUsername("aweriusz.dogwoski");
        userDAO.updateUser(user);

        User updatedUser = userDAO.getUserById(3);
        System.out.printf("new username is %s", updatedUser.getUsername());
*/

/*      Used for testing user deletion -- SUCCEEDED
        System.out.printf("Attempting to delete user %s...\n", userDAO.getUserById(3).getUsername());
        User removedUser = userDAO.deleteUserById(3);

        if (removedUser == null) System.out.println("User deleted successfully");
        else System.err.println("Something bad happened. Oops.");
*/
    }
}
