package dev.richard.app;

import dev.richard.daos.UserDAO;
import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.Password;
import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.utils.ConnectionUtil;
import dev.richard.utils.LogLevel;
import dev.richard.utils.LoggerUtil;
import dev.richard.utils.PasswordUtil;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Password generated = PasswordUtil.generatePassword("testing");
        User u = new User(0, "Zumi", "Daxuya", "zumid@testing.com", "zumid", generated.getHash(), generated.getSalt(), Roles.BASIC);
        new UserDaoPostgres().createUser(u);

    }
}
