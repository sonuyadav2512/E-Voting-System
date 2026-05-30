package model;

import java.sql.Timestamp;

/**
 * Model class representing a user in the E-Voting system.
 */
public class User {

    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private boolean approved;
    private Timestamp createdAt;

    /** Default constructor. */
    public User() { }

    /**
     * Parameterized constructor.
     *
     * @param userId   the user ID
     * @param fullName the full name
     * @param email    the email address
     * @param password the hashed password
     * @param role     the role (admin or voter)
     * @param approved whether the user is approved
     * @param createdAt the creation timestamp
     */
    public User(int userId, String fullName, String email, String password,
                String role, boolean approved, Timestamp createdAt) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.approved = approved;
        this.createdAt = createdAt;
    }

    /** @return the user ID */
    public int getUserId() { return userId; }

    /** @param userId the user ID to set */
    public void setUserId(int userId) { this.userId = userId; }

    /** @return the full name */
    public String getFullName() { return fullName; }

    /** @param fullName the full name to set */
    public void setFullName(String fullName) { this.fullName = fullName; }

    /** @return the email address */
    public String getEmail() { return email; }

    /** @param email the email to set */
    public void setEmail(String email) { this.email = email; }

    /** @return the hashed password */
    public String getPassword() { return password; }

    /** @param password the hashed password to set */
    public void setPassword(String password) { this.password = password; }

    /** @return the user role */
    public String getRole() { return role; }

    /** @param role the role to set */
    public void setRole(String role) { this.role = role; }

    /** @return whether the user is approved */
    public boolean isApproved() { return approved; }

    /** @param approved the approval status to set */
    public void setApproved(boolean approved) { this.approved = approved; }

    /** @return the creation timestamp */
    public Timestamp getCreatedAt() { return createdAt; }

    /** @param createdAt the creation timestamp to set */
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return fullName + " (" + email + ")";
    }
}
