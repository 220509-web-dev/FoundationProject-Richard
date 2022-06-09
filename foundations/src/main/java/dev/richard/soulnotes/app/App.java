package dev.richard.soulnotes.app;

import dev.richard.soulnotes.daos.EmailDaoPostgres;
import dev.richard.soulnotes.daos.UserDaoPostgres;
import dev.richard.soulnotes.entities.EmailReset;
import dev.richard.soulnotes.utils.GenerationUtil;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) throws InterruptedException {
        new EmailDaoPostgres().addReset(new UserDaoPostgres().getUserByEmail("richard@richardmoch.xyz"));
//        System.out.println(LocalDateTime.now().isAfter(test.getExpiration()));
//        Thread.sleep(11000);
//        System.out.println(LocalDateTime.now().isAfter(test.getExpiration()));
    }
}
