package dev.richard.soulnotes.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EmailReset {
    private int userId;
    private String resetToken;
    private LocalDateTime expiration;

    public EmailReset(int userId, String resetToken) {
        this.userId = userId;
        this.resetToken = resetToken;
        this.expiration = LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES));
    }

    public EmailReset(int userId, String resetToken, LocalDateTime expiration) {
        this.userId = userId;
        this.resetToken = resetToken;
        this.expiration = expiration;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    public LocalDateTime getExpiration() {
        return expiration;
    }
}
