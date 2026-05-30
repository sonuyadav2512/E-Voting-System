package dao;

import config.DBConnection;
import model.Election;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the elections table.
 * Provides full CRUD operations using PreparedStatements.
 */
public class ElectionDAO {

    /**
     * Inserts a new election into the database.
     *
     * @param election the {@link Election} to insert
     * @return {@code true} if the insert succeeded
     */
    public boolean insertElection(Election election) {
        String sql = "INSERT INTO elections (title, description, start_date, end_date, status, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, election.getTitle());
            ps.setString(2, election.getDescription());
            ps.setTimestamp(3, new Timestamp(election.getStartDate().getTime()));
            ps.setTimestamp(4, new Timestamp(election.getEndDate().getTime()));
            ps.setString(5, election.getStatus());
            ps.setInt(6, election.getCreatedBy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing election.
     *
     * @param election the {@link Election} with updated fields
     * @return {@code true} if the update succeeded
     */
    public boolean updateElection(Election election) {
        String sql = "UPDATE elections SET title = ?, description = ?, start_date = ?, end_date = ?, status = ? WHERE election_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, election.getTitle());
            ps.setString(2, election.getDescription());
            ps.setTimestamp(3, new Timestamp(election.getStartDate().getTime()));
            ps.setTimestamp(4, new Timestamp(election.getEndDate().getTime()));
            ps.setString(5, election.getStatus());
            ps.setInt(6, election.getElectionId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an election by ID.
     *
     * @param electionId the election ID
     * @return {@code true} if the delete succeeded
     */
    public boolean deleteElection(int electionId) {
        String sql = "DELETE FROM elections WHERE election_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, electionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Finds an election by its ID.
     *
     * @param electionId the election ID
     * @return the {@link Election} if found, or {@code null}
     */
    public Election findById(int electionId) {
        String sql = "SELECT * FROM elections WHERE election_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, electionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapElection(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all elections ordered by creation date descending.
     *
     * @return a list of all {@link Election} objects
     */
    public List<Election> getAllElections() {
        List<Election> elections = new ArrayList<>();
        String sql = "SELECT * FROM elections ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                elections.add(mapElection(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elections;
    }

    /**
     * Retrieves elections by status.
     *
     * @param status the status to filter by (upcoming, active, closed)
     * @return a list of matching {@link Election} objects
     */
    public List<Election> getElectionsByStatus(String status) {
        List<Election> elections = new ArrayList<>();
        String sql = "SELECT * FROM elections WHERE status = ? ORDER BY start_date ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    elections.add(mapElection(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elections;
    }

    /**
     * Updates only the status of an election.
     *
     * @param electionId the election ID
     * @param status     the new status
     * @return {@code true} if the update succeeded
     */
    public boolean updateStatus(int electionId, String status) {
        String sql = "UPDATE elections SET status = ? WHERE election_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, electionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Counts the total number of elections.
     *
     * @return the election count
     */
    public int countElections() {
        String sql = "SELECT COUNT(*) FROM elections";
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
     * Counts elections by status.
     *
     * @param status the status to count
     * @return the count of elections with the given status
     */
    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM elections WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Maps a ResultSet row to an Election object.
     *
     * @param rs the ResultSet positioned at a row
     * @return the mapped {@link Election}
     * @throws SQLException if a database error occurs
     */
    private Election mapElection(ResultSet rs) throws SQLException {
        Election e = new Election();
        e.setElectionId(rs.getInt("election_id"));
        e.setTitle(rs.getString("title"));
        e.setDescription(rs.getString("description"));
        e.setStartDate(rs.getTimestamp("start_date"));
        e.setEndDate(rs.getTimestamp("end_date"));
        e.setStatus(rs.getString("status"));
        e.setCreatedBy(rs.getInt("created_by"));
        e.setCreatedAt(rs.getTimestamp("created_at"));
        return e;
    }
}
