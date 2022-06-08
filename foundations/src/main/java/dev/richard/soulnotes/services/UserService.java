package dev.richard.soulnotes.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.richard.soulnotes.daos.UserDAO;
import dev.richard.soulnotes.dtos.*;
import dev.richard.soulnotes.entities.Password;
import dev.richard.soulnotes.entities.Roles;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.exceptions.EmailAlreadyUsedException;
import dev.richard.soulnotes.exceptions.InvalidCredentialsException;
import dev.richard.soulnotes.exceptions.UserNotFoundException;
import dev.richard.soulnotes.exceptions.UsernameAlreadyUsedException;
import dev.richard.soulnotes.utils.GenerationUtil;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAO userDAO;
    private String exceptionMsg;
    private ErrorResponse error;
    private List<User> users;
    ObjectNode responseNodes;
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
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



        if (user.getUsername().length() < 3) throw new InvalidCredentialsException("Username must be at least 4 characters.");
        if (user.getPassword().length() < 8) throw new InvalidCredentialsException("Password must be at least 8 characters.");

        Password p = GenerationUtil.generatePassword(user.getPassword());
        user.setPasswordHash(p.getHash());
        user.setSalt(p.getSalt());
        user.setRoleId(1);
        user.setRoleType(Roles.BASIC);

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
        if (u == null) throw new UserNotFoundException("Cannot find user with provided id.");
        return u;
    }
}
