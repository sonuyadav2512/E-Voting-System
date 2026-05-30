package utils;

import model.User;

/**
 * Singleton session manager that holds the currently logged-in user.
 */
public class SessionManager {

    /** The current logged-in user. */
    private static User currentUser;

    /** Private constructor to prevent instantiation. */
    private SessionManager() { }

    /**
     * Sets the currently logged-in user.
     *
     * @param user the authenticated {@link User}
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the current {@link User}, or {@code null} if no user is logged in
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks whether the current user has admin privileges.
     *
     * @return {@code true} if the current user is an admin
     */
    public static boolean isAdmin() {
        return currentUser != null && "admin".equals(currentUser.getRole());
    }

    /**
     * Checks whether the current user is a voter.
     *
     * @return {@code true} if the current user is a voter
     */
    public static boolean isVoter() {
        return currentUser != null && "voter".equals(currentUser.getRole());
    }

    /**
     * Clears the session (logout).
     */
    public static void clearSession() {
        currentUser = null;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return {@code true} if a user session exists
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
