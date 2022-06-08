package dev.richard.soulnotes.app;

import dev.richard.soulnotes.daos.UserDaoPostgres;
import dev.richard.soulnotes.utils.GenerationUtil;

public class App {
    public static void main(String[] args) {
        new UserDaoPostgres().deleteUserById(8);
    }
}
