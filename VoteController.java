package controller;

import dao.AuditDAO;
import dao.VoteDAO;
import model.Vote;
import utils.SessionManager;

import java.util.List;
import java.util.Map;

/**
 * Controller for voting operations.
 */
public class VoteController {

    private final VoteDAO voteDAO;
    private final AuditDAO auditDAO;

    /** Constructs a VoteController with default DAOs. */
    public VoteController() {
        this.voteDAO = new VoteDAO();
        this.auditDAO = new AuditDAO();
    }

    /**
     * Casts a vote for a candidate in an election.
     *
     * @param electionId  the election ID
     * @param candidateId the candidate ID
     * @return {@code true} if the vote was successfully cast
     */
    public boolean castVote(int electionId, int candidateId) {
        int voterId = SessionManager.getCurrentUser().getUserId();

        // Double-check at application level
        if (voteDAO.hasVoted(electionId, voterId)) {
            return false;
        }

        Vote vote = new Vote();
        vote.setElectionId(electionId);
        vote.setVoterId(voterId);
        vote.setCandidateId(candidateId);

        boolean result = voteDAO.castVote(vote);
        if (result) {
            auditDAO.log(voterId, "VOTE_CAST",
                    "Vote cast in election " + electionId + " for candidate " + candidateId);
        }
        return result;
    }

    /**
     * Checks if the current voter has voted in a specific election.
     *
     * @param electionId the election ID
     * @return {@code true} if the voter has already voted
     */
    public boolean hasVoted(int electionId) {
        int voterId = SessionManager.getCurrentUser().getUserId();
        return voteDAO.hasVoted(electionId, voterId);
    }

    /**
     * Gets the vote results for an election.
     *
     * @param electionId the election ID
     * @return a map of candidate_id → vote count
     */
    public Map<Integer, Integer> getResults(int electionId) {
        return voteDAO.getResultsByElection(electionId);
    }

    /**
     * Counts total votes in an election.
     *
     * @param electionId the election ID
     * @return the total vote count
     */
    public int countVotes(int electionId) {
        return voteDAO.countVotesByElection(electionId);
    }

    /**
     * Counts all votes across all elections.
     *
     * @return the total vote count
     */
    public int countAllVotes() {
        return voteDAO.countAllVotes();
    }

    /**
     * Gets all election IDs the current voter has voted in.
     *
     * @return list of election IDs
     */
    public List<Integer> getVotedElectionIds() {
        int voterId = SessionManager.getCurrentUser().getUserId();
        return voteDAO.getVotedElectionIds(voterId);
    }

    /**
     * Gets all votes by the current voter.
     *
     * @return list of voter's votes
     */
    public List<Vote> getMyVotes() {
        int voterId = SessionManager.getCurrentUser().getUserId();
        return voteDAO.getVotesByVoter(voterId);
    }
}
