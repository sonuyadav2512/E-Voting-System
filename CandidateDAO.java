package dao;

import config.DBConnection;
import model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the candidates table.
 * Provides full CRUD operations using PreparedStatements.
 */
public class CandidateDAO {

    /**
     * Inserts a new candidate into the database.
     *
     * @param candidate the {@link Candidate} to insert
     * @return {@code true} if the insert succeeded
     */
    public boolean insertCandidate(Candidate candidate) {
        String sql = "INSERT INTO candidates (election_id, full_name, party, bio, photo_path) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, candidate.getElectionId());
            ps.setString(2, candidate.getFullName());
            ps.setString(3, candidate.getParty());
            ps.setString(4, candidate.getBio());
            ps.setString(5, candidate.getPhotoPath());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing candidate.
     *
     * @param candidate the {@link Candidate} with updated fields
     * @return {@code true} if the update succeeded
     */
    public boolean updateCandidate(Candidate candidate) {
        String sql = "UPDATE candidates SET full_name = ?, party = ?, bio = ?, photo_path = ? WHERE candidate_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, candidate.getFullName());
            ps.setString(2, candidate.getParty());
            ps.setString(3, candidate.getBio());
            ps.setString(4, candidate.getPhotoPath());
            ps.setInt(5, candidate.getCandidateId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a candidate by ID.
     *
     * @param candidateId the candidate ID
     * @return {@code true} if the delete succeeded
     */
    public boolean deleteCandidate(int candidateId) {
        String sql = "DELETE FROM candidates WHERE candidate_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, candidateId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Finds a candidate by ID.
     *
     * @param candidateId the candidate ID
     * @return the {@link Candidate} if found, or {@code null}
     */
    public Candidate findById(int candidateId) {
        String sql = "SELECT * FROM candidates WHERE candidate_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, candidateId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCandidate(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all candidates for a specific election.
     *
     * @param electionId the election ID
     * @return a list of {@link Candidate} objects
     */
    public List<Candidate> getCandidatesByElection(int electionId) {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates WHERE election_id = ? ORDER BY full_name ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, electionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    candidates.add(mapCandidate(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    /**
     * Counts candidates in a specific election.
     *
     * @param electionId the election ID
     * @return the candidate count
     */
    public int countByElection(int electionId) {
        String sql = "SELECT COUNT(*) FROM candidates WHERE election_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, electionId);
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
     * Maps a ResultSet row to a Candidate object.
     *
     * @param rs the ResultSet positioned at a row
     * @return the mapped {@link Candidate}
     * @throws SQLException if a database error occurs
     */
    private Candidate mapCandidate(ResultSet rs) throws SQLException {
        Candidate c = new Candidate();
        c.setCandidateId(rs.getInt("candidate_id"));
        c.setElectionId(rs.getInt("election_id"));
        c.setFullName(rs.getString("full_name"));
        c.setParty(rs.getString("party"));
        c.setBio(rs.getString("bio"));
        c.setPhotoPath(rs.getString("photo_path"));
        c.setCreatedAt(rs.getTimestamp("created_at"));
        return c;
    }
}
