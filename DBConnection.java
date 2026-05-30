package config;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection factory for managing MySQL database connections.
 * Uses JDBC Connector/J to connect to XAMPP MySQL.
 * Each call to {@link #getConnection()} returns a new connection
 * suitable for use with try-with-resources in DAO classes.
 */
public class DBConnection {

    /** JDBC connection URL. */
    private static final String URL = "jdbc:mysql://localhost:3306/evoting_db?useSSL=false&autoReconnect=true&serverTimezone=UTC";

    /** Database username (XAMPP default). */
    private static final String USER = "root";

    /** Database password (XAMPP default is empty). */
    private static final String PASSWORD = "";

    /** Whether the JDBC driver has been loaded. */
    private static boolean driverLoaded = false;

    /** Private constructor to prevent instantiation. */
    private DBConnection() { }

    /**
     * Returns a new database connection.
     * Each DAO call should obtain its own connection via try-with-resources.
     *
     * @return a new active {@link Connection}
     */
    public static Connection getConnection() {
        try {
            if (!driverLoaded) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                driverLoaded = true;
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "MySQL JDBC Driver not found.\nPlease add mysql-connector-j JAR to the classpath.",
                    "Driver Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to connect to the database.\nEnsure XAMPP MySQL is running.\n\n" + e.getMessage(),
                    "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}

