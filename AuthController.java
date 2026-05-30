package controller;

import dao.AuditDAO;
import dao.UserDAO;
import model.User;
import utils.HashUtil;
import utils.SessionManager;

/**
 * Controller for authentication operations (login and registration).
 */
public class AuthController {

    private final UserDAO userDAO;
    private final AuditDAO auditDAO;

    /** Constructs an AuthController with default DAOs. */
    public AuthController() {
        this.userDAO = new UserDAO();
        this.auditDAO = new AuditDAO();
    }

    /**
     * Attempts to log in a user with the given credentials.
     *
     * @param email    the user's email
     * @param password the plain-text password
     * @return the authenticated {@link User}, or {@code null} on failure
     */
    public User login(String email, String password) {
        String hashedPassword = HashUtil.sha256(password);
        User user = userDAO.authenticate(email, hashedPassword);

        if (user != null) {
            if (!user.isApproved()) {
                auditDAO.log(user.getUserId(), "LOGIN_FAIL",
                        "Account not approved: " + email);
                return null;
            }
            SessionManager.setCurrentUser(user);
            auditDAO.log(user.getUserId(), "LOGIN_SUCCESS",
                    "User logged in: " + email);
            return user;
        } else {
            // Log failed attempt - find user to get ID if exists
            User existingUser = userDAO.findByEmail(email);
            int userId = (existingUser != null) ? existingUser.getUserId() : 0;
            auditDAO.log(userId, "LOGIN_FAIL",
                    "Failed login attempt for: " + email);
            return null;
        }
    }

    /**
     * Checks if a login failure was due to unapproved account.
     *
     * @param email    the user's email
     * @param password the plain-text password
     * @return {@code true} if the account exists but is not approved
     */
    public boolean isUnapproved(String email, String password) {
        String hashedPassword = HashUtil.sha256(password);
        User user = userDAO.authenticate(email, hashedPassword);
        return user != null && !user.isApproved();
    }

    /**
     * Registers a new voter.
     *
     * @param fullName the full name
     * @param email    the email address
     * @param password the plain-text password
     * @return {@code true} if registration succeeded
     */
    public boolean register(String fullName, String email, String password) {
        // Check if email already exists
        if (userDAO.findByEmail(email) != null) {
            return false;
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(HashUtil.sha256(password));
        user.setRole("voter");
        user.setApproved(false);

        boolean result = userDAO.insertUser(user);
        if (result) {
            auditDAO.log(0, "VOTER_REGISTERED",
                    "New voter registered: " + email);
        }
        return result;
    }

    /**
     * Logs the current user out.
     */
    public void logout() {
        User current = SessionManager.getCurrentUser();
        if (current != null) {
            auditDAO.log(current.getUserId(), "LOGOUT",
                    "User logged out: " + current.getEmail());
        }
        SessionManager.clearSession();
    }

    /**
     * Validates an email format.
     *
     * @param email the email to validate
     * @return {@code true} if the email format is valid
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validates password strength.
     *
     * @param password the password to validate
     * @return {@code true} if password meets minimum requirements (6+ chars)
     */
    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    /**
     * Validates full name format.
     *
     * @param fullName the full name to validate
     * @return {@code true} if name is not empty and reasonable length
     */
    public boolean isValidFullName(String fullName) {
        return fullName != null && fullName.length() >= 2 && fullName.length() <= 100;
    }
}
