package dao;

import config.DBConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the users table.
 * Provides full CRUD operations using PreparedStatements.
 */
public class UserDAO {

    /**
     * Inserts a new user into the database.
     *
     * @param user the {@link User} to insert
     * @return {@code true} if the insert succeeded
     */
    public boolean insertUser(User user) {
        String sql = "INSERT INTO users (full_name, email, password, role, is_approved) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isApproved());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates a user by email and hashed password.
     *
     * @param email    the user's email
     * @param password the hashed password
     * @return the authenticated {@link User}, or {@code null} if not found
     */
    public User authenticate(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return the {@link User} if found, or {@code null}
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds a user by their user ID.
     *
     * @param userId the user ID
     * @return the {@link User} if found, or {@code null}
     */
    public User findById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all voters from the database.
     *
     * @return a list of voter {@link User} objects
     */
    public List<User> getAllVoters() {
        List<User> voters = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'voter' ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                voters.add(mapUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voters;
    }

    /**
     * Retrieves voters filtered by approval status.
     *
     * @param approved the approval status to filter by
     * @return a list of voter {@link User} objects
     */
    public List<User> getVotersByApproval(boolean approved) {
        List<User> voters = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'voter' AND is_approved = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, approved);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    voters.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voters;
    }

    /**
     * Searches voters by name or email.
     *
     * @param keyword the search keyword
     * @return a list of matching voter {@link User} objects
     */
    public List<User> searchVoters(String keyword) {
        List<User> voters = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'voter' AND (full_name LIKE ? OR email LIKE ?) ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    voters.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voters;
    }

    /**
     * Approves a voter by setting is_approved to 1.
     *
     * @param userId the voter's user ID
     * @return {@code true} if the update succeeded
     */
    public boolean approveVoter(int userId) {
        String sql = "UPDATE users SET is_approved = 1 WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Rejects (un-approves) a voter by setting is_approved to 0.
     *
     * @param userId the voter's user ID
     * @return {@code true} if the update succeeded
     */
    public boolean rejectVoter(int userId) {
        String sql = "UPDATE users SET is_approved = 0 WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId the user ID to delete
     * @return {@code true} if the delete succeeded
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Counts the total number of voters.
     *
     * @return the voter count
     */
    public int countVoters() {
        String sql = "SELECT COUNT(*) FROM users WHERE role = 'voter'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Checks if any admin account exists in the database.
     *
     * @return {@code true} if at least one admin exists
     */
    public boolean hasAdminAccount() {
        String sql = "SELECT COUNT(*) FROM users WHERE role = 'admin'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Maps a ResultSet row to a User object.
     *
     * @param rs the result set positioned at a row
     * @return the mapped {@link User}
     * @throws SQLException if a database error occurs
     */
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setApproved(rs.getBoolean("is_approved"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}
