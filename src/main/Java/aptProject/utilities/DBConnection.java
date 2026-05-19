package aptProject.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection — utility class that provides JDBC connections to the MySQL database.
 *
 * <p>Every DAO calls {@link #getConnection()} to obtain a fresh connection.
 * Connections are opened per-request and closed by the DAO using try-with-resources,
 * so no connection pool is needed for this project scale.</p>
 *
 * <p>Database: {@code order_management_system} on {@code localhost:3306}</p>
 */
public final class DBConnection {

    // ── Connection settings ───────────────────────────────────────────────────

    /** JDBC URL pointing to the local MySQL instance and target database */
    private static final String URL = "jdbc:mysql://localhost:3306/order_management_system";

    /** MySQL username (default root for local development) */
    private static final String USER = "root";

    /** MySQL password (empty string for local development — change for production) */
    private static final String PASSWORD = "";

    // ── Constructor ───────────────────────────────────────────────────────────

    /** Private constructor — this class should never be instantiated. */
    private DBConnection() {}

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Opens and returns a new JDBC {@link Connection} to the database.
     *
     * <p>The MySQL JDBC driver is loaded explicitly via {@code Class.forName}
     * to ensure compatibility with environments where the driver is not
     * auto-discovered from the classpath.</p>
     *
     * @return an open {@link Connection} ready for use
     * @throws SQLException if the driver is missing or the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load the MySQL Connector/J driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Wrap as SQLException so callers only need to handle one exception type
            throw new SQLException("MySQL JDBC driver not found.", e);
        }

        // Open and return a new connection using the configured URL and credentials
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
