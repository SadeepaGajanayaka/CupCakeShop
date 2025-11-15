package view.gui;

import controller.CupcakeShopSystem;
import model.User;
import model.Cashier;
import model.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


 //LoginFrame - GUI Login window for The Sweet Cupcake Shop
 //Demonstrates GUI implementation with Swing
public class LoginFrame extends JFrame {

    private CupcakeShopSystem system;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel messageLabel;
    private JPanel backgroundPanel;

    public LoginFrame(CupcakeShopSystem system) {
        this.system = system;
        initializeComponents();
        setupLayout();
        setupListeners();

        // Frame settings
        setTitle("The Sweet Cupcake Shop - Login");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
    }

    // Custom JPanel with background image support
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            // Try to load background image if it exists
            try {
                java.io.File imageFile = new java.io.File("background.png");
                if (imageFile.exists()) {
                    backgroundImage = javax.imageio.ImageIO.read(imageFile);
                }
            } catch (Exception e) {
                // If image not found, use default background color
                backgroundImage = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw background image
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Use gradient background if no image
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(255, 240, 245),
                        0, getHeight(), new Color(255, 220, 230)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    private void initializeComponents() {
        // Title
        titleLabel = new JLabel("THE SWEET CUPCAKE SHOP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Buttons
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(144, 238, 144)); // Light green
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(true);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setBackground(new Color(255, 160, 122)); // Light red
        exitButton.setForeground(Color.BLACK);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(true);

        // Message label
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setupLayout() {
        // Use custom background panel
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        backgroundPanel.setOpaque(false);

        // Title panel with subtitle
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(subtitleLabel);

        // Form panel with semi-transparent background
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // Password row
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // Center container for form
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        centerContainer.add(formPanel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BorderLayout());

        JTextArea infoText = new JTextArea(
                "Default Credentials:\n" +
                        "Manager: admin / admin123\n" +
                        "Cashier: cashier1 / cashier123"
        );
        infoText.setFont(new Font("Arial", Font.PLAIN, 11));
        infoText.setEditable(false);
        infoText.setOpaque(false);
        infoText.setForeground(new Color(100, 100, 100));

        // Bottom panel with info and buttons
        JPanel bottomContainer = new JPanel(new BorderLayout(10, 10));
        bottomContainer.setOpaque(false);
        bottomContainer.add(buttonPanel, BorderLayout.CENTER);
        bottomContainer.add(infoText, BorderLayout.SOUTH);
        bottomContainer.add(messageLabel, BorderLayout.NORTH);

        // Add all panels to background panel
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
        backgroundPanel.add(centerContainer, BorderLayout.CENTER);
        backgroundPanel.add(bottomContainer, BorderLayout.SOUTH);

        // Set content pane
        setContentPane(backgroundPanel);
    }

    private void setupListeners() {
        // Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        LoginFrame.this,
                        "Are you sure you want to exit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    system.saveAllData();
                    System.exit(0);
                }
            }
        });

        // Enter key on password field
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password");
            return;
        }

        boolean success = system.login(username, password);

        if (success) {
            User currentUser = system.getCurrentUser();

            // Close login window
            this.dispose();

            // Open appropriate dashboard
            if (currentUser instanceof Manager) {
                new ManagerDashboard(system).setVisible(true);
            } else if (currentUser instanceof Cashier) {
                new CashierDashboard(system).setVisible(true);
            }
        } else {
            messageLabel.setText("Invalid username or password!");
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }
}