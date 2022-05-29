// Adapted from code provided by Adam Ranieri
package dev.richard.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class LoggerUtil {
    /**
     * Logs information about the program to a file. Requires that the LOG_FILE environment variable be set correctly.
     * @param message The message to log.
     * @param level The severity of the message.
     */
    public static void log(String message, LogLevel level) {
        String logInfo;
        Path location = Paths.get(System.getenv("LOG_FILE"));
        try {
            if (!Files.exists(location)) {
                logInfo = String.format("%s - %s - %s\n",level.name(), "Log file created.", LocalDateTime.now());
                Files.write(Paths.get(System.getenv("LOG_FILE")), logInfo.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
            }
            logInfo = String.format("%s - %s - %s\n",level.name(), message, LocalDateTime.now());
            Files.write(Paths.get(System.getenv("LOG_FILE")), logInfo.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}