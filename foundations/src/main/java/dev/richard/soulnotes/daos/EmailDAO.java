package dev.richard.soulnotes.daos;

import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.entities.User;

public interface EmailDAO {
    EmailReset addReset(User user);
    EmailReset getResetToken(int id);
    EmailReset getResetTokenByToken(String token);
    EmailReset getResetToken(String email);
}
