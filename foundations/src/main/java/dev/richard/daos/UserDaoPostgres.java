package dev.richard.daos;

import dev.richard.entities.Password;
import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.exceptions.EmailAlreadyUsedException;
import dev.richard.exceptions.UsernameAlreadyUsedException;
import dev.richard.utils.ConnectionUtil;
import dev.richard.utils.GenerationUtil;
import dev.richard.utils.LogLevel;
import dev.richard.utils.LoggerUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgres implements UserDAO {
    private String logString;

    @Override
    public User createUser(User user) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = String.format("Attempting to create new user with id of %d...", user.getUserId());
            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            System.out.println("creating sql query string");
            String query = "insert into soulnotes.users values (default, ?, ?, ?, ?, ?, ?, ?)";

            System.out.println("creating pstmt");
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            System.out.println("here before param setting");

            // Get all values from the input
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setBytes(5, user.getPasswordHash());
            ps.setBytes(6, user.getSalt());
            ps.setInt(7, user.getRoleId());

            System.out.println("here after param setting");

            ps.execute(); // retrieve the next id
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            System.out.println("here after execute");

            int newId = rs.getInt("id");
            user.setUserId(newId);
            System.out.println(user);
            logString = String.format("Added new user with the username %s and id of %d", user.getUsername(), user.getUserId());
            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            return user;

        } catch (SQLException e) {
            logString = String.format("An error has occurred while attempting to make user with the username of %s and id of %d. Exception details: %s", user.getUsername(), user.getUserId(), ExceptionUtils.getMessage(e));
            e.printStackTrace();
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(int id) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = String.format("Attempting to retrieve user with id of %d...", id);
            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            String query = "select * from soulnotes.users where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            User u = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getBytes("hash"), rs.getBytes("salt"), Roles.from(rs.getInt("role_id")));
            logString = String.format("Retrieved user %s.", u.getUsername());

            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            return u;

        } catch (SQLException e) {
            logString = String.format("Could not retrieve user. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = String.format("Attempting to retrieve user with username of %s...", username);
            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            String query = "select * from soulnotes.users where uname = ?";
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();

            User u = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getBytes("hash"), rs.getBytes("salt"), Roles.from(rs.getInt("role_id")));
            logString = String.format("Retrieved user %s.", u.getUsername());

            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            return u;

        } catch (SQLException e) {
            logString = String.format("Could not retrieve user. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
            e.printStackTrace();

        }
        return null;
    }
    public User getUserByEmail(String email) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = String.format("Attempting to retrieve user with email of %s...", email);
            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            String query = "select * from users where uname = ?";
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rs.next();

            User u = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getBytes("hash"), rs.getBytes("salt"), Roles.from(rs.getInt("role_id")));
            logString = String.format("Retrieved user %s.", u.getUsername());

            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            return u;

        } catch (SQLException e) {
            logString = String.format("Could not retrieve user. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
            e.printStackTrace();

        }
        return null;
    }
    @Override
    public List<User> getAllUsers() {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = "Attempting to retrieve all the users in the database...";
            // LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            String query = "select * from soulnotes.users";
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User u = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getBytes("hash"), rs.getBytes("salt"), Roles.from(rs.getInt("role_id")));
                users.add(u);
            }
            logString = "Retrieved all users in the database.";
            // LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            return users;
        } catch (SQLException e) {
            logString = String.format("Could not retrieve all users in the database. More information: %s", ExceptionUtils.getMessage(e));
            e.printStackTrace();
            // LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = String.format("Attempting to update user with id of %d", user.getUserId());
            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            String query = "update users set fname = ?, lname = ?, email = ?, uname = ?, hash = ?, salt = ?, role_id = ? where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            User oldUser = user;

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            //ps.setString(5, user.getPasswordHash());
            ps.setInt(6, user.getRoleId());
            ps.setInt(7, user.getUserId());

            ps.execute();
            logString = String.format("Updated user successfully. Old user information: %s, new user information: %s", oldUser, user);
            LoggerUtil.getInstance().log(logString, LogLevel.INFO);
            return user;

        } catch (SQLException e) {
            logString = String.format("Failed to update user. More information: %s", ExceptionUtils.getMessage(e));
            e.printStackTrace();
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
        }
        return null;
    }

    @Override
    public User deleteUserById(int id) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = String.format("Attempting to delete user with id of %d...", id);
            String query = "delete from users where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
            logString = String.format("Deleted user with id of %d successfully.", id);
        } catch (SQLException e) {
            logString = String.format("Failed to delete user. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User deleteUserByUsername(String username) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            logString = String.format("Attempting to delete user with username of %s...", username);
            String query = "delete from users where uname = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, username);
            ps.execute();
            logString = String.format("Deleted user with username of %s successfully.", username);
        } catch (SQLException e) {
            logString = String.format("Failed to delete user. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.getInstance().log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }
}
