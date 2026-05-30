package dao;

import config.DBConnection;
import model.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for the votes table.
 * Provides voting operations with double-vote prevention.
 */
public class VoteDAO {

    /**
     * Casts a vote. Enforces one-vote-per-election at the application level.
     *
     * @param vote the {@link Vote} to cast
     * @return {@code true} if the vote was successfully cast
     */
    public boolean castVote(Vote vote) {
        if (hasVoted(vote.getElectionId(), vote.getVoterId())) {
            return false;
        }
        String sql = "INSERT INTO votes (election_id, voter_id, candidate_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vote.getElectionId());
            ps.setInt(2, vote.getVoterId());
            ps.setInt(3, vote.getCandidateId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a voter has already voted in a specific election.
     *
     * @param electionId the election ID
     * @param voterId    the voter's user ID
     * @return {@code true} if the voter has already voted
     */
    public boolean hasVoted(int electionId, int voterId) {
        String sql = "SELECT COUNT(*) FROM votes WHERE election_id = ? AND voter_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, electionId);
            ps.setInt(2, voterId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the vote results for an election: candidate_id → vote count.
     *
     * @param electionId the election ID
     * @return a map of candidate_id to vote count
     */
    public Map<Integer, Integer> getResultsByElection(int electionId) {
        Map<Integer, Integer> results = new LinkedHashMap<>();
        String sql = "SELECT candidate_id, COUNT(*) as vote_count FROM votes WHERE election_id = ? GROUP BY candidate_id ORDER BY vote_count DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, electionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.put(rs.getInt("candidate_id"), rs.getInt("vote_count"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Counts total votes cast in an election.
     *
     * @param electionId the election ID
     * @return the total vote count
     */
    public int countVotesByElection(int electionId) {
        String sql = "SELECT COUNT(*) FROM votes WHERE election_id = ?";
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
     * Counts total votes across all elections.
     *
     * @return the total vote count
     */
    public int countAllVotes() {
        String sql = "SELECT COUNT(*) FROM votes";
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
     * Retrieves all votes cast by a specific voter.
     *
     * @param voterId the voter's user ID
     * @return a list of {@link Vote} objects
     */
    public List<Vote> getVotesByVoter(int voterId) {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes WHERE voter_id = ? ORDER BY voted_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vote v = new Vote();
                    v.setVoteId(rs.getInt("vote_id"));
                    v.setElectionId(rs.getInt("election_id"));
                    v.setVoterId(rs.getInt("voter_id"));
                    v.setCandidateId(rs.getInt("candidate_id"));
                    v.setVotedAt(rs.getTimestamp("voted_at"));
                    votes.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votes;
    }

    /**
     * Gets the election IDs that a voter has voted in.
     *
     * @param voterId the voter's user ID
     * @return a list of election IDs
     */
    public List<Integer> getVotedElectionIds(int voterId) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT election_id FROM votes WHERE voter_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("election_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
