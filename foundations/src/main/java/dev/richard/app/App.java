package dev.richard.app;

import dev.richard.daos.UserDaoPostgres;
import dev.richard.entities.Password;
import dev.richard.entities.Roles;
import dev.richard.entities.User;
import dev.richard.utils.GenerationUtil;

public class App {
    public static void main(String[] args) {
        System.out.println(GenerationUtil.generateResetToken());
    }
}
