package model;

import java.sql.Timestamp;

/**
 * Model class representing a candidate in an election.
 */
public class Candidate {

    private int candidateId;
    private int electionId;
    private String fullName;
    private String party;
    private String bio;
    private String photoPath;
    private Timestamp createdAt;

    /** Default constructor. */
    public Candidate() { }

    /**
     * Parameterized constructor.
     *
     * @param candidateId the candidate ID
     * @param electionId  the election ID
     * @param fullName    the candidate's full name
     * @param party       the party affiliation
     * @param bio         the candidate biography
     * @param photoPath   the path to the candidate's photo
     * @param createdAt   the creation timestamp
     */
    public Candidate(int candidateId, int electionId, String fullName,
                     String party, String bio, String photoPath, Timestamp createdAt) {
        this.candidateId = candidateId;
        this.electionId = electionId;
        this.fullName = fullName;
        this.party = party;
        this.bio = bio;
        this.photoPath = photoPath;
        this.createdAt = createdAt;
    }

    /** @return the candidate ID */
    public int getCandidateId() { return candidateId; }

    /** @param candidateId the candidate ID to set */
    public void setCandidateId(int candidateId) { this.candidateId = candidateId; }

    /** @return the election ID */
    public int getElectionId() { return electionId; }

    /** @param electionId the election ID to set */
    public void setElectionId(int electionId) { this.electionId = electionId; }

    /** @return the candidate's full name */
    public String getFullName() { return fullName; }

    /** @param fullName the full name to set */
    public void setFullName(String fullName) { this.fullName = fullName; }

    /** @return the party affiliation */
    public String getParty() { return party; }

    /** @param party the party to set */
    public void setParty(String party) { this.party = party; }

    /** @return the candidate biography */
    public String getBio() { return bio; }

    /** @param bio the biography to set */
    public void setBio(String bio) { this.bio = bio; }

    /** @return the photo file path */
    public String getPhotoPath() { return photoPath; }

    /** @param photoPath the photo path to set */
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    /** @return the creation timestamp */
    public Timestamp getCreatedAt() { return createdAt; }

    /** @param createdAt the creation timestamp to set */
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return fullName + " (" + party + ")";
    }
}
