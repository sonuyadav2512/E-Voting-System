package model;

import java.sql.Timestamp;

/**
 * Model class representing an audit log entry.
 */
public class AuditLog {

    private int logId;
    private int userId;
    private String action;
    private String details;
    private Timestamp loggedAt;

    /** Default constructor. */
    public AuditLog() { }

    /**
     * Parameterized constructor.
     *
     * @param logId    the log entry ID
     * @param userId   the user ID who performed the action
     * @param action   the action type
     * @param details  additional detail text
     * @param loggedAt the timestamp of the action
     */
    public AuditLog(int logId, int userId, String action, String details, Timestamp loggedAt) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.details = details;
        this.loggedAt = loggedAt;
    }

    /** @return the log entry ID */
    public int getLogId() { return logId; }

    /** @param logId the log entry ID to set */
    public void setLogId(int logId) { this.logId = logId; }

    /** @return the user ID */
    public int getUserId() { return userId; }

    /** @param userId the user ID to set */
    public void setUserId(int userId) { this.userId = userId; }

    /** @return the action type */
    public String getAction() { return action; }

    /** @param action the action to set */
    public void setAction(String action) { this.action = action; }

    /** @return the detail text */
    public String getDetails() { return details; }

    /** @param details the details to set */
    public void setDetails(String details) { this.details = details; }

    /** @return the log timestamp */
    public Timestamp getLoggedAt() { return loggedAt; }

    /** @param loggedAt the timestamp to set */
    public void setLoggedAt(Timestamp loggedAt) { this.loggedAt = loggedAt; }
}
