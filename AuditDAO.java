package dao;

import config.DBConnection;
import model.AuditLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the audit_log table.
 * Provides logging and retrieval operations.
 */
public class AuditDAO {

    /**
     * Inserts an audit log entry.
     *
     * @param userId  the user ID who performed the action (0 for anonymous)
     * @param action  the action type (e.g., LOGIN_SUCCESS, VOTE_CAST)
     * @param details additional detail text
     */
    public void log(int userId, String action, String details) {
        String sql = "INSERT INTO audit_log (user_id, action, details) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, action);
            ps.setString(3, details);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all audit log entries ordered by most recent first.
     *
     * @return a list of {@link AuditLog} entries
     */
    public List<AuditLog> getAllLogs() {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM audit_log ORDER BY logged_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                logs.add(mapAuditLog(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Retrieves audit log entries for a specific user.
     *
     * @param userId the user ID
     * @return a list of {@link AuditLog} entries
     */
    public List<AuditLog> getLogsByUser(int userId) {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM audit_log WHERE user_id = ? ORDER BY logged_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Retrieves audit log entries by action type.
     *
     * @param action the action type to filter by
     * @return a list of {@link AuditLog} entries
     */
    public List<AuditLog> getLogsByAction(String action) {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM audit_log WHERE action = ? ORDER BY logged_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, action);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Maps a ResultSet row to an AuditLog object.
     *
     * @param rs the ResultSet positioned at a row
     * @return the mapped {@link AuditLog}
     * @throws SQLException if a database error occurs
     */
    private AuditLog mapAuditLog(ResultSet rs) throws SQLException {
        AuditLog log = new AuditLog();
        log.setLogId(rs.getInt("log_id"));
        log.setUserId(rs.getInt("user_id"));
        log.setAction(rs.getString("action"));
        log.setDetails(rs.getString("details"));
        log.setLoggedAt(rs.getTimestamp("logged_at"));
        return log;
    }
}
