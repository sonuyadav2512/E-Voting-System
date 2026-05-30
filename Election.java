package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Model class representing an election.
 */
public class Election {

    private int electionId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;
    private int createdBy;
    private Timestamp createdAt;

    /** Default constructor. */
    public Election() { }

    /**
     * Parameterized constructor.
     *
     * @param electionId  the election ID
     * @param title       the election title
     * @param description the election description
     * @param startDate   the start date
     * @param endDate     the end date
     * @param status      the election status
     * @param createdBy   the creator's user ID
     * @param createdAt   the creation timestamp
     */
    public Election(int electionId, String title, String description,
                    Date startDate, Date endDate, String status,
                    int createdBy, Timestamp createdAt) {
        this.electionId = electionId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    /** @return the election ID */
    public int getElectionId() { return electionId; }

    /** @param electionId the election ID to set */
    public void setElectionId(int electionId) { this.electionId = electionId; }

    /** @return the election title */
    public String getTitle() { return title; }

    /** @param title the title to set */
    public void setTitle(String title) { this.title = title; }

    /** @return the election description */
    public String getDescription() { return description; }

    /** @param description the description to set */
    public void setDescription(String description) { this.description = description; }

    /** @return the start date */
    public Date getStartDate() { return startDate; }

    /** @param startDate the start date to set */
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    /** @return the end date */
    public Date getEndDate() { return endDate; }

    /** @param endDate the end date to set */
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    /** @return the election status */
    public String getStatus() { return status; }

    /** @param status the status to set */
    public void setStatus(String status) { this.status = status; }

    /** @return the creator's user ID */
    public int getCreatedBy() { return createdBy; }

    /** @param createdBy the creator's user ID to set */
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    /** @return the creation timestamp */
    public Timestamp getCreatedAt() { return createdAt; }

    /** @param createdAt the creation timestamp to set */
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return title;
    }
}
