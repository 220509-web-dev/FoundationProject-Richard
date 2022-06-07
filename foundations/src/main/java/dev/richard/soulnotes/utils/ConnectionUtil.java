package dev.richard.soulnotes.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static ConnectionUtil connectionUtil;
    private Properties props = new Properties();

    public static ConnectionUtil getInstance() {
        if (connectionUtil == null) connectionUtil = new ConnectionUtil();
        return connectionUtil;
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Failed to load PostgreSQL Driver");
            throw new RuntimeException(e); // fail fast
        }
    }

    private ConnectionUtil() {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            System.err.println("Failed to load database credentials from property file.");
            throw new RuntimeException(e); // fail fast for easier debugging
        }
    }
    /**
     * Attempts a connection to the Postgres database. Requires that the application.properties is set correctly.
     * @return java.sql.Connection
     */
    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(props.getProperty("db-url"),
                                                          props.getProperty("db-username"),
                                                          props.getProperty("db-password"));
            if (conn == null) throw new RuntimeException("Could not establish a database to the connection.");
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
