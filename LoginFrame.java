package view;

import controller.AuthController;
import model.User;
import utils.UITheme;
import view.admin.AdminDashboard;
import view.voter.VoterDashboard;

import javax.swing.*;
import java.awt.*;

/**
 * Login screen for the E-Voting System.
 * Validates credentials and routes to the appropriate dashboard.
 */
public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel errorLabel;
    private JButton loginButton;
    private JButton registerButton;
    private final AuthController authController;

    /**
     * Constructs the login frame.
     */
    public LoginFrame() {
        authController = new AuthController();
        initComponents();
    }

    /**
     * Initializes and lays out all UI components.
     */
    private void initComponents() {
        setTitle("E-Voting System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 580);
        setMinimumSize(new Dimension(420, 520));
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.BG_LIGHT);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UITheme.PRIMARY);
        headerPanel.setPreferredSize(new Dimension(0, 120));
        headerPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("E-Voting System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Secure Electronic Voting");
        subtitleLabel.setFont(UITheme.FONT_BODY);
        subtitleLabel.setForeground(UITheme.ACCENT);

        JPanel headerContent = new JPanel(new GridLayout(2, 1, 0, 2));
        headerContent.setOpaque(false);
        headerContent.add(titleLabel);
        headerContent.add(subtitleLabel);
        headerPanel.add(headerContent);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(UITheme.BG_LIGHT);

        JPanel formPanel = UITheme.createCardPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(380, 340));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Login title
        JLabel loginTitle = new JLabel("Sign In");
        loginTitle.setFont(UITheme.FONT_TITLE);
        loginTitle.setForeground(UITheme.PRIMARY);
        gbc.gridy = 0;
        formPanel.add(loginTitle, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(UITheme.FONT_BODY);
        emailLabel.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 15, 2, 15);
        formPanel.add(emailLabel, gbc);

        emailField = UITheme.createTextField();
        gbc.gridy = 2;
        gbc.insets = new Insets(2, 15, 8, 15);
        formPanel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(UITheme.FONT_BODY);
        passwordLabel.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 15, 2, 15);
        formPanel.add(passwordLabel, gbc);

        passwordField = UITheme.createPasswordField();
        gbc.gridy = 4;
        gbc.insets = new Insets(2, 15, 8, 15);
        formPanel.add(passwordField, gbc);

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.DANGER);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 15, 5, 15);
        formPanel.add(errorLabel, gbc);

        // Login button
        loginButton = UITheme.createPrimaryButton("Login");
        loginButton.setPreferredSize(new Dimension(0, UITheme.BUTTON_HEIGHT));
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 15, 8, 15);
        formPanel.add(loginButton, gbc);

        // Register link
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setOpaque(false);
        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(UITheme.FONT_BODY);
        noAccountLabel.setForeground(UITheme.TEXT_SECONDARY);

        registerButton = new JButton("Register");
        registerButton.setFont(UITheme.FONT_BUTTON);
        registerButton.setForeground(UITheme.PRIMARY);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        registerPanel.add(noAccountLabel);
        registerPanel.add(registerButton);
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 15, 5, 15);
        formPanel.add(registerPanel, gbc);

        formContainer.add(formPanel);
        mainPanel.add(formContainer, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // ─── Event handlers ────────────────────────
        loginButton.addActionListener(e -> performLogin());
        registerButton.addActionListener(e -> openRegisterFrame());
        passwordField.addActionListener(e -> performLogin());

        // Clear error on typing
        emailField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { clearError(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { clearError(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { clearError(); }
        });
    }

    /**
     * Performs the login operation in a SwingWorker.
     */
    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Validate
        boolean valid = true;
        if (email.isEmpty()) {
            UITheme.setErrorBorder(emailField);
            valid = false;
        } else {
            UITheme.resetBorder(emailField);
        }
        if (password.isEmpty()) {
            UITheme.setErrorBorder(passwordField);
            valid = false;
        } else {
            UITheme.resetBorder(passwordField);
        }

        if (!valid) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        loginButton.setEnabled(false);
        loginButton.setText("Logging in...");

        SwingWorker<User, Void> worker = new SwingWorker<>() {
            private boolean unapproved = false;

            @Override
            protected User doInBackground() {
                User user = authController.login(email, password);
                if (user == null) {
                    unapproved = authController.isUnapproved(email, password);
                }
                return user;
            }

            @Override
            protected void done() {
                loginButton.setEnabled(true);
                loginButton.setText("Login");
                try {
                    User user = get();
                    if (user != null) {
                        dispose();
                        if ("admin".equals(user.getRole())) {
                            new AdminDashboard().setVisible(true);
                        } else {
                            new VoterDashboard().setVisible(true);
                        }
                    } else if (unapproved) {
                        errorLabel.setText("Account pending approval. Please wait for admin.");
                    } else {
                        errorLabel.setText("Invalid email or password.");
                        UITheme.setErrorBorder(emailField);
                        UITheme.setErrorBorder(passwordField);
                    }
                } catch (Exception ex) {
                    errorLabel.setText("An error occurred. Please try again.");
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /**
     * Opens the registration frame.
     */
    private void openRegisterFrame() {
        new RegisterFrame(this).setVisible(true);
    }

    /**
     * Clears the error label.
     */
    private void clearError() {
        errorLabel.setText(" ");
    }
}
