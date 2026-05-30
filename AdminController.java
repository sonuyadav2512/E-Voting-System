package controller;

import dao.AuditDAO;
import dao.UserDAO;
import model.User;
import utils.SessionManager;

import java.util.List;

/**
 * Controller for admin-specific operations (voter management).
 */
public class AdminController {

    private final UserDAO userDAO;
    private final AuditDAO auditDAO;

    /** Constructs an AdminController with default DAOs. */
    public AdminController() {
        this.userDAO = new UserDAO();
        this.auditDAO = new AuditDAO();
    }

    /**
     * Retrieves all voters.
     *
     * @return the list of all voter users
     */
    public List<User> getAllVoters() {
        return userDAO.getAllVoters();
    }

    /**
     * Retrieves voters filtered by approval status.
     *
     * @param approved the approval status
     * @return the list of matching voters
     */
    public List<User> getVotersByApproval(boolean approved) {
        return userDAO.getVotersByApproval(approved);
    }

    /**
     * Searches voters by name or email.
     *
     * @param keyword the search keyword
     * @return the list of matching voters
     */
    public List<User> searchVoters(String keyword) {
        return userDAO.searchVoters(keyword);
    }

    /**
     * Approves a voter registration.
     *
     * @param userId the voter's user ID
     * @return {@code true} if approval succeeded
     */
    public boolean approveVoter(int userId) {
        boolean result = userDAO.approveVoter(userId);
        if (result) {
            User voter = userDAO.findById(userId);
            String voterName = (voter != null) ? voter.getFullName() : String.valueOf(userId);
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "VOTER_APPROVED", "Approved voter: " + voterName);
        }
        return result;
    }

    /**
     * Rejects a voter registration.
     *
     * @param userId the voter's user ID
     * @return {@code true} if rejection succeeded
     */
    public boolean rejectVoter(int userId) {
        boolean result = userDAO.rejectVoter(userId);
        if (result) {
            User voter = userDAO.findById(userId);
            String voterName = (voter != null) ? voter.getFullName() : String.valueOf(userId);
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "VOTER_REJECTED", "Rejected voter: " + voterName);
        }
        return result;
    }

    /**
     * Deletes a voter account.
     *
     * @param userId the voter's user ID
     * @return {@code true} if deletion succeeded
     */
    public boolean deleteVoter(int userId) {
        User voter = userDAO.findById(userId);
        boolean result = userDAO.deleteUser(userId);
        if (result && voter != null) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "VOTER_DELETED", "Deleted voter: " + voter.getFullName());
        }
        return result;
    }

    /**
     * Bulk approves a list of voters.
     *
     * @param userIds the list of user IDs to approve
     * @return the number of successfully approved voters
     */
    public int bulkApprove(List<Integer> userIds) {
        int count = 0;
        for (int id : userIds) {
            if (approveVoter(id)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts total voters.
     *
     * @return the voter count
     */
    public int countVoters() {
        return userDAO.countVoters();
    }

    /**
     * Adds a new admin account.
     *
     * @param admin the admin user to add
     * @return {@code true} if admin was created successfully
     */
    public boolean addAdmin(User admin) {
        if (userDAO.findByEmail(admin.getEmail()) != null) {
            return false; // Email already exists
        }
        return userDAO.insertUser(admin);
    }
}
