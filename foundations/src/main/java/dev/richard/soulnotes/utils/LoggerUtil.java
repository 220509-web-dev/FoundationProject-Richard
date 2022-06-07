// Adapted from code provided by Adam Ranieri
package dev.richard.soulnotes.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Properties;

public class LoggerUtil {
    private Properties props = new Properties();
    private String logInfo;
    private static LoggerUtil loggerUtil;
    public static LoggerUtil getInstance() {
        if (loggerUtil == null) loggerUtil = new LoggerUtil();
        return loggerUtil;
    }
    private LoggerUtil() {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Could not find property from properties file.");
        }
    }
    /**
     * Logs information about the program to a file. Requires that the application.properties file be set correctly.
     * @param message The message to log.
     * @param level The severity of the message.
     */
    public void log(String message, LogLevel level) {
        try {
            System.out.println(props.getProperty("log-dir"));
            //
//            if (!Files.exists(Paths.get(props.getProperty("log-dir")))) {
//                logInfo = String.format("[%s]: %s - %s\n", level.name(), "Created new log.", LocalDateTime.now());
//                Files.write(Paths.get(props.getProperty("log-dir")), logInfo.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
//            }
            logInfo = String.format("[%s]: %s - %s\n", level.name(), message, LocalDateTime.now());
            Files.write(Paths.get(props.getProperty("log-dir")), logInfo.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}