package dev.richard.daos;
import dev.richard.entities.User;

import java.util.List;

public interface UserDAO {
    /**
     * Puts a new user into the database.
     * @param user The user object to add.
     * @return dev.richard.entities.User
     */
    // Create
    User createUser(User user);
    // Read

    /**
     * Grabs a user in the database by their user ID.
     * @param id The user ID to use.
     * @return dev.richard.entities.User
     */

    User getUserById(int id);

    /**
     * Grabs a user in the database by their username.
     * @param username The username to use.
     * @return dev.richard.entities.User
     */

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    /**
     * Gets all users in the database.
     * @return java.util.List
     */
    List<User> getAllUsers();
    // Update

    /**
     * Updates information about a user in the database.
     * @param user The user object to pass.
     * @return dev.richard.entities.User
     */
    User updateUser(User user);
    // Delete

    /**
     * Deletes a user from the database.
     * @param id The ID to delete.
     * @return null if successful
     */
    User deleteUserById(int id);

    /**
     * 
     * @param username The username to delete.
     * @return null if successful
     */
    User deleteUserByUsername(String username);
}
