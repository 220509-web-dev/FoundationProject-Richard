package dev.richard.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    /**
     * Attempts a connection to the Postgres database. Requires that the DB_CONNECTION variable is set correctly.
     * @return java.sql.Connection
     */
    public static Connection getConnection() {
        try {
            String dbInfo = System.getenv("DB_CONNECTION");
            return DriverManager.getConnection(dbInfo);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
