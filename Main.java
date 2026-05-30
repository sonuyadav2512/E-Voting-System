package main;

import dao.UserDAO;
import view.LoginFrame;
import view.SetupWizardFrame;

import javax.swing.*;

/**
 * Entry point for the E-Voting System application.
 * Initializes the FlatLaf Look & Feel and opens the Login screen or Setup Wizard.
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            // Try FlatLaf first
            Class<?> flatLafClass = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            UIManager.setLookAndFeel(flatLafClass.getDeclaredConstructor().newInstance().getClass().getName());
        } catch (ClassNotFoundException e) {
            // FlatLaf not available, use Nimbus as fallback
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                // Fall back to system default
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) { }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configure global UI defaults
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("TextComponent.arc", 6);

        // Launch the appropriate frame on the EDT
        SwingUtilities.invokeLater(() -> {
            UserDAO userDAO = new UserDAO();
            if (userDAO.hasAdminAccount()) {
                // Admin exists, show login
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            } else {
                // No admin exists, show setup wizard
                SetupWizardFrame setupFrame = new SetupWizardFrame();
                setupFrame.setVisible(true);
            }
        });
    }
}
