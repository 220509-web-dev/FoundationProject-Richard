package dev.richard.soulnotes.daos;

import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.entities.User;
import dev.richard.soulnotes.utils.ConnectionUtil;
import dev.richard.soulnotes.utils.GenerationUtil;
import dev.richard.soulnotes.utils.LogLevel;
import dev.richard.soulnotes.utils.LoggerUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
