package utils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Centralized UI theme configuration for the E-Voting System.
 * Defines colors, fonts, and custom UI components for consistent styling.
 */
public class UITheme {

    // ─── COLOR CONSTANTS ──────────────────────────
    /** Primary navy blue. */
    public static final Color PRIMARY = new Color(0x1A, 0x23, 0x7E);

    /** Primary dark variant. */
    public static final Color PRIMARY_DARK = new Color(0x0D, 0x14, 0x52);

    /** Primary light variant. */
    public static final Color PRIMARY_LIGHT = new Color(0x53, 0x4B, 0xAE);

    /** Gold accent color. */
    public static final Color ACCENT = new Color(0xFF, 0xD7, 0x00);

    /** White background. */
    public static final Color BG_WHITE = Color.WHITE;

    /** Light gray background for cards. */
    public static final Color BG_LIGHT = new Color(0xF5, 0xF5, 0xF5);

    /** Card background. */
    public static final Color BG_CARD = Color.WHITE;

    /** Success green. */
    public static final Color SUCCESS = new Color(0x4C, 0xAF, 0x50);

    /** Error / danger red. */
    public static final Color DANGER = new Color(0xF4, 0x43, 0x36);

    /** Warning orange. */
    public static final Color WARNING = new Color(0xFF, 0x98, 0x00);

    /** Text primary. */
    public static final Color TEXT_PRIMARY = new Color(0x21, 0x21, 0x21);

    /** Text secondary (muted). */
    public static final Color TEXT_SECONDARY = new Color(0x75, 0x75, 0x75);

    /** Sidebar background. */
    public static final Color SIDEBAR_BG = PRIMARY_DARK;

    /** Sidebar text color. */
    public static final Color SIDEBAR_TEXT = Color.WHITE;

    // ─── FONT CONSTANTS ───────────────────────────
    /** Title font (18pt Bold). */
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    /** Subtitle font (14pt Bold). */
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 14);

    /** Body font (12pt Plain). */
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 12);

    /** Table font (11pt Plain). */
    public static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 11);

    /** Button font (12pt Bold). */
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 12);

    /** Small font (10pt). */
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 10);

    // ─── DIMENSION CONSTANTS ──────────────────────
    /** Default field height. */
    public static final int FIELD_HEIGHT = 35;

    /** Default button height. */
    public static final int BUTTON_HEIGHT = 38;

    /** Default sidebar width. */
    public static final int SIDEBAR_WIDTH = 220;

    /** Default padding. */
    public static final int PADDING = 15;

    /** Private constructor. */
    private UITheme() { }

    /**
     * Creates a styled primary button with rounded corners.
     *
     * @param text the button label
     * @return a styled {@link JButton}
     */
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width + 30, BUTTON_HEIGHT));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return button;
    }

    /**
     * Creates a styled accent (gold) button with rounded corners.
     *
     * @param text the button label
     * @return a styled {@link JButton}
     */
    public static JButton createAccentButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setForeground(PRIMARY_DARK);
        button.setBackground(ACCENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width + 30, BUTTON_HEIGHT));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return button;
    }

    /**
     * Creates a styled danger (red) button.
     *
     * @param text the button label
     * @return a styled {@link JButton}
     */
    public static JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setForeground(Color.WHITE);
        button.setBackground(DANGER);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return button;
    }

    /**
     * Creates a styled text field with consistent sizing.
     *
     * @return a styled {@link JTextField}
     */
    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_BODY);
        field.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBD, 0xBD, 0xBD)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    /**
     * Creates a styled password field with consistent sizing.
     *
     * @return a styled {@link JPasswordField}
     */
    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FONT_BODY);
        field.setPreferredSize(new Dimension(250, FIELD_HEIGHT));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBD, 0xBD, 0xBD)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    /**
     * Creates a card panel with a subtle shadow border and white background.
     *
     * @return a styled {@link JPanel}
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BG_CARD);
        panel.setBorder(createShadowBorder());
        return panel;
    }

    /**
     * Creates a shadow border for card-style panels.
     *
     * @return a {@link Border} simulating a drop shadow
     */
    public static Border createShadowBorder() {
        return BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        );
    }

    /**
     * Creates a stats card for dashboards.
     *
     * @param title the card title
     * @param value the displayed value
     * @param color the accent color for the top stripe
     * @return a styled {@link JPanel}
     */
    public static JPanel createStatsCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(createShadowBorder());
        card.setPreferredSize(new Dimension(200, 100));

        // Top color stripe
        JPanel stripe = new JPanel();
        stripe.setBackground(color);
        stripe.setPreferredSize(new Dimension(0, 4));
        card.add(stripe, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel(new GridLayout(2, 1, 0, 5));
        content.setBackground(BG_CARD);
        content.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_BODY);
        titleLabel.setForeground(TEXT_SECONDARY);

        content.add(valueLabel);
        content.add(titleLabel);
        card.add(content, BorderLayout.CENTER);

        return card;
    }

    /**
     * Sets a red error border on a text component.
     *
     * @param component the component to highlight
     */
    public static void setErrorBorder(JComponent component) {
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DANGER, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    /**
     * Resets the border on a text component to the default.
     *
     * @param component the component to reset
     */
    public static void resetBorder(JComponent component) {
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBD, 0xBD, 0xBD)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    /**
     * Custom border that simulates a drop shadow around a panel.
     */
    private static class ShadowBorder extends AbstractBorder {
        private static final int SHADOW_SIZE = 3;
        private static final Color SHADOW_COLOR = new Color(0, 0, 0, 40);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < SHADOW_SIZE; i++) {
                g2.setColor(new Color(0, 0, 0, 20 - (i * 5)));
                g2.drawRoundRect(x + i, y + i, width - (i * 2) - 1, height - (i * 2) - 1, 8, 8);
            }
            g2.setColor(new Color(0xE0, 0xE0, 0xE0));
            g2.drawRoundRect(x + SHADOW_SIZE, y + SHADOW_SIZE,
                    width - (SHADOW_SIZE * 2) - 1, height - (SHADOW_SIZE * 2) - 1, 6, 6);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(SHADOW_SIZE + 1, SHADOW_SIZE + 1, SHADOW_SIZE + 1, SHADOW_SIZE + 1);
        }
    }
}
