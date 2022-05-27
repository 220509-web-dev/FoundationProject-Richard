package dev.richard.daos;

import dev.richard.entities.User;
import dev.richard.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgres implements UserDAO {

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
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
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

            return new User(id, rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getString("pword"), rs.getInt("role_id"));

        } catch (SQLException e) {
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
                User u = new User(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("uname"), rs.getString("pword"), rs.getInt("role_id"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        try (Connection c = ConnectionUtil.getConnection()) {
            String query = "update users set fname = ?, lname = ?, email = ?, uname = ?, pword = ?, role_id = ? where id = ?";
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getRoleId());
            ps.execute();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
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
