package view;

import controller.AdminController;
import controller.AuthController;
import model.User;
import utils.HashUtil;
import utils.UITheme;

import javax.swing.*;
import java.awt.*;

/**
 * First-time setup wizard for creating the initial admin account.
 * Displays on first run if no admin exists in the database.
 */
public class SetupWizardFrame extends JFrame {

    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel errorLabel;
    private JButton createButton;
    private JButton skipButton;
    private final AdminController adminController;
    private final AuthController authController;

    public SetupWizardFrame() {
        adminController = new AdminController();
        authController = new AuthController();
        initComponents();
    }

    private void initComponents() {
        setTitle("E-Voting System - Initial Setup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 680);
        setMinimumSize(new Dimension(460, 600));
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

        JLabel titleLabel = new JLabel("Initial Setup");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Create your admin account");
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
        formContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel formPanel = UITheme.createCardPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(420, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title
        JLabel formTitle = new JLabel("Admin Account Details");
        formTitle.setFont(UITheme.FONT_TITLE);
        formTitle.setForeground(UITheme.PRIMARY);
        gbc.gridy = 0;
        formPanel.add(formTitle, gbc);

        // Full Name
        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setFont(UITheme.FONT_BODY);
        fullNameLabel.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 15, 2, 15);
        formPanel.add(fullNameLabel, gbc);

        fullNameField = UITheme.createTextField();
        gbc.gridy = 2;
        gbc.insets = new Insets(2, 15, 8, 15);
        formPanel.add(fullNameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(UITheme.FONT_BODY);
        emailLabel.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 15, 2, 15);
        formPanel.add(emailLabel, gbc);

        emailField = UITheme.createTextField();
        gbc.gridy = 4;
        gbc.insets = new Insets(2, 15, 8, 15);
        formPanel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(UITheme.FONT_BODY);
        passwordLabel.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 15, 2, 15);
        formPanel.add(passwordLabel, gbc);

        passwordField = UITheme.createPasswordField();
        gbc.gridy = 6;
        gbc.insets = new Insets(2, 15, 8, 15);
        formPanel.add(passwordField, gbc);

        // Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(UITheme.FONT_BODY);
        confirmLabel.setForeground(UITheme.TEXT_SECONDARY);
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 15, 2, 15);
        formPanel.add(confirmLabel, gbc);

        confirmPasswordField = UITheme.createPasswordField();
        gbc.gridy = 8;
        gbc.insets = new Insets(2, 15, 8, 15);
        formPanel.add(confirmPasswordField, gbc);

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.DANGER);
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 15, 5, 15);
        formPanel.add(errorLabel, gbc);

        // Password strength indicator
        JLabel strengthLabel = new JLabel("Password strength: ");
        strengthLabel.setFont(UITheme.FONT_SMALL);
        JLabel strengthValue = new JLabel("None");
        strengthValue.setFont(UITheme.FONT_SMALL);
        strengthValue.setForeground(UITheme.TEXT_SECONDARY);

        JPanel strengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        strengthPanel.setOpaque(false);
        strengthPanel.add(strengthLabel);
        strengthPanel.add(strengthValue);

        gbc.gridy = 10;
        gbc.insets = new Insets(0, 15, 8, 15);
        formPanel.add(strengthPanel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);

        skipButton = new JButton("Skip for Now");
        skipButton.setFont(UITheme.FONT_BUTTON);
        skipButton.setForeground(UITheme.TEXT_SECONDARY);
        skipButton.setBackground(UITheme.BG_LIGHT);
        skipButton.setFocusPainted(false);
        skipButton.setBorder(BorderFactory.createLineBorder(UITheme.BORDER, 1));
        skipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        createButton = UITheme.createPrimaryButton("Create Admin Account");
        createButton.setPreferredSize(new Dimension(0, UITheme.BUTTON_HEIGHT));

        buttonPanel.add(skipButton);
        buttonPanel.add(createButton);

        gbc.gridy = 11;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        formPanel.add(buttonPanel, gbc);

        formContainer.add(formPanel);
        mainPanel.add(formContainer, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Event handlers
        createButton.addActionListener(e -> createAdminAccount());
        skipButton.addActionListener(e -> skipSetup());

        // Update password strength on typing
        passwordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePasswordStrength(strengthValue); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePasswordStrength(strengthValue); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePasswordStrength(strengthValue); }
        });

        // Clear error on typing
        emailField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { clearError(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { clearError(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { clearError(); }
        });
    }

    private void updatePasswordStrength(JLabel strengthValue) {
        String password = new String(passwordField.getPassword());
        String strength = HashUtil.getPasswordStrength(password);
        strengthValue.setText(strength);

        if ("Weak".equals(strength)) {
            strengthValue.setForeground(UITheme.DANGER);
        } else if ("Medium".equals(strength)) {
            strengthValue.setForeground(new Color(255, 193, 7)); // Orange
        } else {
            strengthValue.setForeground(UITheme.SUCCESS);
        }
    }

    private void clearError() {
        errorLabel.setText(" ");
    }

    private void createAdminAccount() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation using AuthController
        if (!authController.isValidFullName(fullName)) {
            showError("Please enter a valid full name (2-100 characters).");
            UITheme.setErrorBorder(fullNameField);
            return;
        }
        UITheme.resetBorder(fullNameField);

        if (!authController.isValidEmail(email)) {
            showError("Please enter a valid email address.");
            UITheme.setErrorBorder(emailField);
            return;
        }
        UITheme.resetBorder(emailField);

        if (!authController.isValidPassword(password)) {
            showError("Password must be at least 6 characters long.");
            UITheme.setErrorBorder(passwordField);
            return;
        }
        UITheme.resetBorder(passwordField);

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            UITheme.setErrorBorder(confirmPasswordField);
            return;
        }
        UITheme.resetBorder(confirmPasswordField);

        createButton.setEnabled(false);
        createButton.setText("Creating...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                User admin = new User();
                admin.setFullName(fullName);
                admin.setEmail(email);
                admin.setPassword(HashUtil.sha256(password));
                admin.setRole("admin");
                admin.setApproved(true);

                return adminController.addAdmin(admin);
            }

            @Override
            protected void done() {
                try {
                    Boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(SetupWizardFrame.this,
                                "Admin account created successfully!\n\nYou can now log in with your email and password.",
                                "Setup Complete", JOptionPane.INFORMATION_MESSAGE);
                        openLoginFrame();
                    } else {
                        showError("Failed to create admin account. Email may already exist.");
                        createButton.setEnabled(true);
                        createButton.setText("Create Admin Account");
                    }
                } catch (Exception e) {
                    showError("Error: " + e.getMessage());
                    createButton.setEnabled(true);
                    createButton.setText("Create Admin Account");
                }
            }
        };
        worker.execute();
    }

    private void skipSetup() {
        int response = JOptionPane.showConfirmDialog(this,
                "Skip setup? You can use the default admin credentials:\n\n" +
                "Email: admin@evoting.com\nPassword: Admin@123\n\n" +
                "You can create additional admin accounts later.",
                "Confirm Skip", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            openLoginFrame();
        }
    }

    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose();
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }
}
