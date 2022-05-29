package dev.richard.daos;

import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.utils.ConnectionUtil;
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
        try (Connection c = ConnectionUtil.getConnection()) {
            String query = "insert into foundation_app.users values (default, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Get all values from the input
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getRoleId());

            ps.execute(); // retrieve the next id
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int newId = rs.getInt("id");
            user.setUserId(newId);
            logString = String.format("Added new user with the username %s and id of %d", user.getUsername(), user.getUserId());
            LoggerUtil.log(logString, LogLevel.INFO);
            return user;

        } catch (SQLException e) {
            logString = String.format("An error has occurred while attempting to make user with the username of %s and id of %d. Exception details: %s", user.getUsername(), user.getUserId(), ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
            LoggerUtil.log(logString, LogLevel.ERROR);
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        try (Connection c = ConnectionUtil.getConnection()) {
            String query = "select * from users where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            User u = new User(id, rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getString("pword"), Roles.from(rs.getInt("role_id")));
            logString = String.format("Retrieved user %s.", u.getUsername());

            LoggerUtil.log(logString, LogLevel.INFO);
            return u;

        } catch (SQLException e) {
            logString = String.format("Could not retrieve user. More information: %s", ExceptionUtils.getStackTrace(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        try (Connection c = ConnectionUtil.getConnection()) {
        String query = "select * from users where uname = ?";
        PreparedStatement ps = c.prepareStatement(query);

        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        rs.next();

        User u = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getString("pword"), Roles.from(rs.getInt("role_id")));
        logString = String.format("Retrieved user %s.", u.getUsername());

        LoggerUtil.log(logString, LogLevel.INFO);
        return u;

        } catch (SQLException e) {
            logString = String.format("Could not retrieve user. More information: %s", ExceptionUtils.getStackTrace(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();

        }
        return null;
    }
    @Override
    public List<User> getAllUsers() {
        try (Connection c = ConnectionUtil.getConnection()) {
            String query = "select * from users";
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User u = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getString("pword"), Roles.from(rs.getInt("role_id")));
                users.add(u);
            }
            logString = "Retrieved all users in the database.";
            LoggerUtil.log(logString, LogLevel.INFO);
            return users;
        } catch (SQLException e) {
            logString = String.format("Could not retrieve all users in the database. More information: %s", ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
            LoggerUtil.log(logString, LogLevel.ERROR);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        try (Connection c = ConnectionUtil.getConnection()) {
            String query = "update users set fname = ?, lname = ?, email = ?, uname = ?, pword = ?, role_id = ? where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            User oldUser = user;

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getRoleId());
            ps.setInt(7, user.getUserId());

            ps.execute();
            logString = String.format("Updated user successfully. Old user information: %s, new user information: %s", oldUser, user);
            LoggerUtil.log(logString, LogLevel.INFO);
            return user;

        } catch (SQLException e) {
            logString = String.format("Failed to update user. More information: %s", ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
            LoggerUtil.log(logString, LogLevel.ERROR);
        }
        return null;
    }

    @Override
    public User deleteUserById(int id) {
        try (Connection c = ConnectionUtil.getConnection()) {
        String query = "delete from users where id = ?";
        PreparedStatement ps = c.prepareStatement(query);

        ps.setInt(1, id);
        ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
