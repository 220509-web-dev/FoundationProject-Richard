package dev.richard.daos;
import dev.richard.entities.User;

import java.util.List;

public interface UserDAO {
    // Create
    User createUser(User user);
    // Read
    User getUserById(int id);
    List<User> getAllUsers();
    // Update
    User updateUser(User user);
    // Delete
    User deleteUserById(int id);
}
