package dev.richard.soulnotes.services;

import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.dtos.ResourceCreationResponse;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.exceptions.EmailAlreadyUsedException;
import dev.richard.soulnotes.exceptions.InvalidCredentialsException;
import dev.richard.soulnotes.exceptions.UserNotFoundException;
import dev.richard.soulnotes.exceptions.UsernameAlreadyUsedException;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO;
    private String exceptionMsg;
    private List<User> users;
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        users = userDAO.getAllUsers();
    }

    public ResourceCreationResponse createUser(User user) {
        if (user == null || user.getFirstName() == null || user.getFirstName().equals("") ||
                user.getLastName() == null || user.getLastName().equals("") ||
                user.getUsername() == null || user.getUsername().equals("") ||
                user.getPassword() == null || user.getPassword().equals("") ||
                user.getEmail() == null || user.getEmail().equals("")) {
            exceptionMsg = "Provided information must not be null.";
            throw new InvalidCredentialsException(exceptionMsg);
        }
        users = users.stream().filter(foundUser -> foundUser.getUsername() == user.getUsername()).collect(Collectors.toList());
        if (!users.isEmpty()) throw new UsernameAlreadyUsedException("This username is already in use.");

        users = userDAO.getAllUsers().stream().filter(foundUser -> foundUser.getEmail() == user.getEmail()).collect(Collectors.toList()); // this might be verbose but i just wanna make sure that it is properly filtered
        if (!users.isEmpty()) throw new EmailAlreadyUsedException("This email is already in use.");

        if (user.getUsername().length() > 3) throw new InvalidCredentialsException("Username must be at least 4 characters.");
        if (user.getPassword().length() > 8) throw new InvalidCredentialsException("Password must be at least 8 characters.");

        return new ResourceCreationResponse(userDAO.createUser(user).getUserId());
    }

    public List<User> getUsers() {
        return users;
    }
    public User getUserByUsername(String username) {
        User u = userDAO.getUserByUsername(username);

        if (u == null) throw new UserNotFoundException("Cannot find user with provided username.");

        return u;
     }
     public User getUserById(int id) {
        User u = userDAO.getUserById(id);
        if (u == null) throw new UserNotFoundException("Cannot find user with provided username.");
     }
}
