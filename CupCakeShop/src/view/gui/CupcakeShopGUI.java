package view.gui;

import controller.CupcakeShopSystem;

import javax.swing.*;
import java.awt.*;


 //CupcakeShopGUI - Main entry point for the GUI application
 //Launches the Swing-based graphical interface
public class CupcakeShopGUI {

    public static void main(String[] args) {
        // Set Look and Feel to system default for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default if system L&F fails
            e.printStackTrace();
        }

        // Run GUI on Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Show loading splash with callback to initialize system after
                showSplashScreen(() -> {
                    // Initialize system after splash completes
                    CupcakeShopSystem system = new CupcakeShopSystem();

                    // Show login frame
                    LoginFrame loginFrame = new LoginFrame(system);
                    loginFrame.setVisible(true);
                });
            }
        });
    }

    private static void showSplashScreen(Runnable onComplete) {
        final JWindow splash = new JWindow();
        splash.setSize(450, 300);
        splash.setLocationRelativeTo(null);

        // Main panel with background image support
        JPanel content = new JPanel(new GridBagLayout()) {
            private Image backgroundImage;

            {
                // Try multiple paths to load background image
                String[] bgPaths = {
                        "/resources/splash_background.png",
                        "resources/splash_background.png",
                        "/splash_background.png",
                        "splash_background.png"
                };

                for (String path : bgPaths) {
                    try {
                        java.net.URL imgURL = CupcakeShopGUI.class.getResource(path);
                        if (imgURL != null) {
                            ImageIcon bgIcon = new ImageIcon(imgURL);
                            backgroundImage = bgIcon.getImage();
                            System.out.println("✓ Background loaded from: " + path);
                            break;
                        }
                    } catch (Exception e) {
                        // Try next path
                    }
                }

                if (backgroundImage == null) {
                    System.out.println("⚠ Background image not found - using solid color");
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // Draw background image scaled to fit
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        content.setBackground(new java.awt.Color(255, 240, 245));
        content.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Try to load logo/icon image with multiple paths
        boolean iconLoaded = false;
        String[] iconPaths = {
                "/resources/splash.png",
                "resources/splash.png",
                "/splash.png",
                "splash.png"
        };

        for (String path : iconPaths) {
            try {
                java.net.URL imgURL = CupcakeShopGUI.class.getResource(path);
                if (imgURL != null) {
                    ImageIcon splashIcon = new ImageIcon(imgURL);
                    Image scaledImage = splashIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    content.add(imageLabel, gbc);
                    gbc.gridy++;
                    iconLoaded = true;
                    System.out.println("✓ Icon loaded from: " + path);
                    break;
                }
            } catch (Exception e) {
                // Try next path
            }
        }

        if (!iconLoaded) {
            System.out.println("⚠ Icon image not found - continuing without it");
        }

        // Title with shadow effect for better visibility on background
        JLabel titleLabel = new JLabel("THE SWEET CUPCAKE SHOP", SwingConstants.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 26));
        titleLabel.setForeground(new java.awt.Color(139, 69, 19));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(titleLabel, gbc);
        gbc.gridy++;

        // Subtitle
        gbc.insets = new Insets(4, 20, 4, 20);
        JLabel subtitleLabel = new JLabel("Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
        subtitleLabel.setForeground(new java.awt.Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(subtitleLabel, gbc);
        gbc.gridy++;

        // Version
        JLabel versionLabel = new JLabel("Version 1.0", SwingConstants.CENTER);
        versionLabel.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 12));
        versionLabel.setForeground(new java.awt.Color(120, 120, 120));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(versionLabel, gbc);
        gbc.gridy++;

        // Loading label
        gbc.insets = new Insets(20, 20, 8, 20);
        JLabel loadingLabel = new JLabel("Loading System...", SwingConstants.CENTER);
        loadingLabel.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 14));
        loadingLabel.setForeground(new java.awt.Color(80, 80, 80));
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(loadingLabel, gbc);
        gbc.gridy++;

        // Progress bar - FIXED for visibility and animation
        gbc.insets = new Insets(8, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(false);

        // Much more visible colors
        progressBar.setForeground(new java.awt.Color(255, 20, 147));  // Deep pink - very visible!
        progressBar.setBackground(new java.awt.Color(220, 220, 220));  // Light gray
        progressBar.setBorderPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(new java.awt.Color(150, 150, 150), 2));
        progressBar.setPreferredSize(new Dimension(250, 22));

        content.add(progressBar, gbc);

        splash.setContentPane(content);
        splash.setVisible(true);

        // Animate the progress bar with a timer
        final Timer progressTimer = new Timer(30, null);
        progressTimer.addActionListener(new java.awt.event.ActionListener() {
            private int progress = 0;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                progress += 2;
                progressBar.setValue(progress);

                if (progress >= 100) {
                    progressTimer.stop();
                }
            }
        });
        progressTimer.start();

        // Close splash after 2 seconds and run callback
        Timer closeTimer = new Timer(2000, e -> {
            progressTimer.stop();
            splash.dispose();
            if (onComplete != null) {
                onComplete.run();
            }
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
    }
}