package dev.richard.soulnotes.daos;

import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.exceptions.UserNotFoundException;
import dev.richard.soulnotes.utils.ConnectionUtil;
import dev.richard.soulnotes.utils.GenerationUtil;
import dev.richard.soulnotes.utils.LogLevel;
import dev.richard.soulnotes.utils.LoggerUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EmailDaoPostgres implements EmailDAO {
    @Override
    public EmailReset addReset(User user) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            EmailReset reset = new EmailReset(user.getUserId(), GenerationUtil.generateResetToken());
            String query = "insert into reset_tokens values (?, ?, ?)";

            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, user.getUserId());
            ps.setString(2, reset.getResetToken());
            ps.setString(3, reset.getExpiration().toString());

            ps.execute();

            return reset;
        } catch (SQLException e) {
            LoggerUtil.getInstance().log(ExceptionUtils.getStackTrace(e), LogLevel.ERROR);
            return null;
        }
    }

    @Override
    public EmailReset getResetToken(int id) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            String query = "select * from reset_tokens where user_id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            EmailReset reset = new EmailReset(rs.getInt("user_id"), rs.getString("token"), LocalDateTime.parse(rs.getString("datetime")));
            return reset;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmailReset getResetTokenByToken(String token) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            String query = "select * from reset_tokens where token = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            rs.next();

            EmailReset reset = new EmailReset(rs.getInt("user_id"), rs.getString("token"), LocalDateTime.parse(rs.getString("datetime")));
            return reset;
        } catch (SQLException e) {
            LoggerUtil.getInstance().log(ExceptionUtils.getStackTrace(e), LogLevel.ERROR);
        }
        return null;
    }

    @Override
    public EmailReset getResetToken(String email) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            UserDAO userDAO = new UserDaoPostgres();
            User user = userDAO.getUserByEmail(email);

            if (user == null) {
                throw new UserNotFoundException();
            }
            String query = "select * from reset_tokens where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, user.getUserId());
            ResultSet rs = ps.executeQuery();
            rs.next();

            EmailReset reset = new EmailReset(rs.getInt("user_id"), rs.getString("token"), LocalDateTime.parse(rs.getString("datetime")));
            return reset;
        } catch (SQLException e) {
            LoggerUtil.getInstance().log(ExceptionUtils.getStackTrace(e), LogLevel.ERROR);
        }
        return null;
    }

    @Override
    public void deleteResetToken(EmailReset reset) {
        try (Connection c = ConnectionUtil.getInstance().getConnection()) {
            String query = "delete from reset_tokens where token = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, reset.getResetToken());
            ps.execute();
        } catch (SQLException e) {
            LoggerUtil.getInstance().log(ExceptionUtils.getStackTrace(e), LogLevel.ERROR);
        }
    }
}
