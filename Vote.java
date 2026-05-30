package model;

import java.sql.Timestamp;

/**
 * Model class representing a vote cast by a voter.
 */
public class Vote {

    private int voteId;
    private int electionId;
    private int voterId;
    private int candidateId;
    private Timestamp votedAt;

    /** Default constructor. */
    public Vote() { }

    /**
     * Parameterized constructor.
     *
     * @param voteId      the vote ID
     * @param electionId  the election ID
     * @param voterId     the voter's user ID
     * @param candidateId the candidate ID
     * @param votedAt     the vote timestamp
     */
    public Vote(int voteId, int electionId, int voterId, int candidateId, Timestamp votedAt) {
        this.voteId = voteId;
        this.electionId = electionId;
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.votedAt = votedAt;
    }

    /** @return the vote ID */
    public int getVoteId() { return voteId; }

    /** @param voteId the vote ID to set */
    public void setVoteId(int voteId) { this.voteId = voteId; }

    /** @return the election ID */
    public int getElectionId() { return electionId; }

    /** @param electionId the election ID to set */
    public void setElectionId(int electionId) { this.electionId = electionId; }

    /** @return the voter's user ID */
    public int getVoterId() { return voterId; }

    /** @param voterId the voter's user ID to set */
    public void setVoterId(int voterId) { this.voterId = voterId; }

    /** @return the candidate ID */
    public int getCandidateId() { return candidateId; }

    /** @param candidateId the candidate ID to set */
    public void setCandidateId(int candidateId) { this.candidateId = candidateId; }

    /** @return the vote timestamp */
    public Timestamp getVotedAt() { return votedAt; }

    /** @param votedAt the vote timestamp to set */
    public void setVotedAt(Timestamp votedAt) { this.votedAt = votedAt; }
}
