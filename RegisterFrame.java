package view;

import controller.AuthController;
import utils.HashUtil;
import utils.UITheme;

import javax.swing.*;
import java.awt.*;

/**
 * Registration screen for new voters.
 * Includes password strength indicator and validation.
 */
public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel nameError;
    private JLabel emailError;
    private JLabel passwordError;
    private JLabel confirmError;
    private JLabel strengthLabel;
    private JProgressBar strengthBar;
    private JButton registerButton;
    private final AuthController authController;
    private final JFrame parentFrame;

    /**
     * Constructs the registration frame.
     *
     * @param parent the parent login frame
     */
    public RegisterFrame(JFrame parent) {
        this.parentFrame = parent;
        this.authController = new AuthController();
        initComponents();
    }

    /**
     * Initializes and lays out all UI components.
     */
    private void initComponents() {
        setTitle("E-Voting System - Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 680);
        setMinimumSize(new Dimension(450, 620));
        setLocationRelativeTo(parentFrame);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UITheme.BG_LIGHT);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UITheme.PRIMARY);
        headerPanel.setPreferredSize(new Dimension(0, 90));
        headerPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(UITheme.BG_LIGHT);

        JPanel formPanel = UITheme.createCardPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(420, 480));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 15, 2, 15);

        int row = 0;

        // Full Name
        gbc.gridy = row++;
        gbc.insets = new Insets(10, 15, 2, 15);
        formPanel.add(createLabel("Full Name"), gbc);
        gbc.gridy = row++;
        gbc.insets = new Insets(2, 15, 0, 15);
        nameField = UITheme.createTextField();
        formPanel.add(nameField, gbc);
        gbc.gridy = row++;
        nameError = createErrorLabel();
        formPanel.add(nameError, gbc);

        // Email
        gbc.gridy = row++;
        gbc.insets = new Insets(5, 15, 2, 15);
        formPanel.add(createLabel("Email Address"), gbc);
        gbc.gridy = row++;
        gbc.insets = new Insets(2, 15, 0, 15);
        emailField = UITheme.createTextField();
        formPanel.add(emailField, gbc);
        gbc.gridy = row++;
        emailError = createErrorLabel();
        formPanel.add(emailError, gbc);

        // Password
        gbc.gridy = row++;
        gbc.insets = new Insets(5, 15, 2, 15);
        formPanel.add(createLabel("Password"), gbc);
        gbc.gridy = row++;
        gbc.insets = new Insets(2, 15, 0, 15);
        passwordField = UITheme.createPasswordField();
        formPanel.add(passwordField, gbc);

        // Strength indicator
        gbc.gridy = row++;
        gbc.insets = new Insets(3, 15, 0, 15);
        JPanel strengthPanel = new JPanel(new BorderLayout(8, 0));
        strengthPanel.setOpaque(false);
        strengthBar = new JProgressBar(0, 100);
        strengthBar.setPreferredSize(new Dimension(0, 6));
        strengthBar.setBorderPainted(false);
        strengthLabel = new JLabel("Password Strength");
        strengthLabel.setFont(UITheme.FONT_SMALL);
        strengthLabel.setForeground(UITheme.TEXT_SECONDARY);
        strengthPanel.add(strengthBar, BorderLayout.CENTER);
        strengthPanel.add(strengthLabel, BorderLayout.EAST);
        formPanel.add(strengthPanel, gbc);

        gbc.gridy = row++;
        passwordError = createErrorLabel();
        formPanel.add(passwordError, gbc);

        // Confirm Password
        gbc.gridy = row++;
        gbc.insets = new Insets(5, 15, 2, 15);
        formPanel.add(createLabel("Confirm Password"), gbc);
        gbc.gridy = row++;
        gbc.insets = new Insets(2, 15, 0, 15);
        confirmPasswordField = UITheme.createPasswordField();
        formPanel.add(confirmPasswordField, gbc);
        gbc.gridy = row++;
        confirmError = createErrorLabel();
        formPanel.add(confirmError, gbc);

        // Register button
        gbc.gridy = row++;
        gbc.insets = new Insets(15, 15, 5, 15);
        registerButton = UITheme.createAccentButton("Register");
        registerButton.setPreferredSize(new Dimension(0, UITheme.BUTTON_HEIGHT));
        formPanel.add(registerButton, gbc);

        // Back to login
        gbc.gridy = row;
        gbc.insets = new Insets(5, 15, 10, 15);
        JButton backButton = new JButton("Back to Login");
        backButton.setFont(UITheme.FONT_BUTTON);
        backButton.setForeground(UITheme.PRIMARY);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formPanel.add(backButton, gbc);

        formContainer.add(formPanel);
        mainPanel.add(formContainer, BorderLayout.CENTER);
        setContentPane(mainPanel);

        // ─── Event handlers ────────────────────────
        registerButton.addActionListener(e -> performRegister());
        backButton.addActionListener(e -> dispose());

        // Password strength listener
        passwordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateStrength(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateStrength(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateStrength(); }
        });
    }

    /**
     * Updates the password strength indicator.
     */
    private void updateStrength() {
        String pw = new String(passwordField.getPassword());
        String strength = HashUtil.getPasswordStrength(pw);
        switch (strength) {
            case "Weak":
                strengthBar.setValue(33);
                strengthBar.setForeground(UITheme.DANGER);
                strengthLabel.setText("Weak");
                strengthLabel.setForeground(UITheme.DANGER);
                break;
            case "Medium":
                strengthBar.setValue(66);
                strengthBar.setForeground(UITheme.WARNING);
                strengthLabel.setText("Medium");
                strengthLabel.setForeground(UITheme.WARNING);
                break;
            case "Strong":
                strengthBar.setValue(100);
                strengthBar.setForeground(UITheme.SUCCESS);
                strengthLabel.setText("Strong");
                strengthLabel.setForeground(UITheme.SUCCESS);
                break;
        }
    }

    /**
     * Validates input and performs registration in a SwingWorker.
     */
    private void performRegister() {
        clearErrors();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());

        boolean valid = true;

        if (name.isEmpty()) {
            nameError.setText("Full name is required.");
            UITheme.setErrorBorder(nameField);
            valid = false;
        }
        if (email.isEmpty()) {
            emailError.setText("Email is required.");
            UITheme.setErrorBorder(emailField);
            valid = false;
        } else if (!authController.isValidEmail(email)) {
            emailError.setText("Invalid email format.");
            UITheme.setErrorBorder(emailField);
            valid = false;
        }
        if (password.isEmpty()) {
            passwordError.setText("Password is required.");
            UITheme.setErrorBorder(passwordField);
            valid = false;
        } else if (password.length() < 6) {
            passwordError.setText("Password must be at least 6 characters.");
            UITheme.setErrorBorder(passwordField);
            valid = false;
        }
        if (!password.equals(confirm)) {
            confirmError.setText("Passwords do not match.");
            UITheme.setErrorBorder(confirmPasswordField);
            valid = false;
        }

        if (!valid) return;

        registerButton.setEnabled(false);
        registerButton.setText("Registering...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return authController.register(name, email, password);
            }

            @Override
            protected void done() {
                registerButton.setEnabled(true);
                registerButton.setText("Register");
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(RegisterFrame.this,
                                "Registration submitted successfully!\nPlease wait for admin approval.",
                                "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        emailError.setText("Email already registered.");
                        UITheme.setErrorBorder(emailField);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "An error occurred. Please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /**
     * Creates a styled field label.
     *
     * @param text the label text
     * @return the styled JLabel
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.FONT_BODY);
        label.setForeground(UITheme.TEXT_SECONDARY);
        return label;
    }

    /**
     * Creates an inline error label.
     *
     * @return the styled error JLabel
     */
    private JLabel createErrorLabel() {
        JLabel label = new JLabel(" ");
        label.setFont(UITheme.FONT_SMALL);
        label.setForeground(UITheme.DANGER);
        return label;
    }

    /**
     * Clears all error labels and resets field borders.
     */
    private void clearErrors() {
        nameError.setText(" ");
        emailError.setText(" ");
        passwordError.setText(" ");
        confirmError.setText(" ");
        UITheme.resetBorder(nameField);
        UITheme.resetBorder(emailField);
        UITheme.resetBorder(passwordField);
        UITheme.resetBorder(confirmPasswordField);
    }
}
