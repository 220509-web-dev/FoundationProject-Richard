package dev.richard.soulnotes.app;

import dev.richard.soulnotes.services.EmailService;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        EmailService.sendResetEmail("richard@richardmoch.xyz");
    }
}
