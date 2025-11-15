package view.gui;

import controller.CupcakeShopSystem;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


 //ManagerDashboard - Main GUI for Manager users with extended features

public class ManagerDashboard extends JFrame {

    private CupcakeShopSystem system;
    private JTable cupcakeTable;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    private JLabel welcomeLabel;
    private JPanel backgroundPanel;

    public ManagerDashboard(CupcakeShopSystem system) {
        this.system = system;
        initializeComponents();
        setupLayout();
        loadCupcakeData();

        setTitle("The Sweet Cupcake Shop - Manager Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Custom JPanel with background image support
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                java.io.File imageFile = new java.io.File("background.png");
                if (imageFile.exists()) {
                    backgroundImage = javax.imageio.ImageIO.read(imageFile);
                }
            } catch (Exception e) {
                backgroundImage = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
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
        User currentUser = system.getCurrentUser();
        welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + " (Manager)");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(139, 69, 19));

        // Table
        String[] columnNames = {"ID", "Name", "Category", "Flavor", "Price", "Stock", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cupcakeTable = new JTable(tableModel);
        cupcakeTable.setFont(new Font("Arial", Font.PLAIN, 12));
        cupcakeTable.setRowHeight(25);
        cupcakeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        cupcakeTable.getTableHeader().setBackground(new Color(255, 182, 193));

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
    }

    private void setupLayout() {
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("THE SWEET CUPCAKE SHOP - MANAGER PANEL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(139, 69, 19));
        titlePanel.add(titleLabel);

        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Create tabs
        tabbedPane.addTab("Inventory", createInventoryTab());
        tabbedPane.addTab("Manage Cupcakes", createManageCupcakesTab());
        tabbedPane.addTab("User Management", createUserManagementTab());
        tabbedPane.addTab("Reports", createReportsTab());

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createInventoryTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(cupcakeTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Inventory"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton refreshButton = createButton("Refresh", new Color(255, 222, 173));
        JButton searchCategoryButton = createButton("Search by Category", new Color(173, 216, 230));
        JButton searchNameButton = createButton("Search by Name", new Color(173, 216, 230));
        JButton viewAllButton = createButton("View All", new Color(144, 238, 144));
        JButton logoutButton = createButton("Logout", new Color(255, 160, 122));

        refreshButton.addActionListener(e -> loadCupcakeData());
        searchCategoryButton.addActionListener(e -> showSearchByCategoryDialog());
        searchNameButton.addActionListener(e -> showSearchByNameDialog());
        viewAllButton.addActionListener(e -> loadCupcakeData());
        logoutButton.addActionListener(e -> performLogout());

        buttonPanel.add(refreshButton);
        buttonPanel.add(searchCategoryButton);
        buttonPanel.add(searchNameButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(logoutButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createManageCupcakesTab() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add Cupcake button with custom icon
        JButton addButton = createLargeButtonWithIcon("Add New Cupcake", 
                new Color(144, 238, 144), "add_cupcake.png");
        addButton.addActionListener(e -> showAddCupcakeDialog());

        // Update Stock button with custom icon
        JButton updateButton = createLargeButtonWithIcon("Update Stock", 
                new Color(173, 216, 230), "update_stock.png");
        updateButton.addActionListener(e -> showUpdateStockDialog());

        // Remove Cupcake button with custom icon
        JButton removeButton = createLargeButtonWithIcon("Remove Cupcake", 
                new Color(255, 160, 122), "remove_cupcake.png");
        removeButton.addActionListener(e -> showRemoveCupcakeDialog());

        // Add Category button with custom icon
        JButton categoryButton = createLargeButtonWithIcon("Add Category", 
                new Color(255, 222, 173), "add_category.png");
        categoryButton.addActionListener(e -> showAddCategoryDialog());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(removeButton);
        panel.add(categoryButton);

        return panel;
    }

    private JPanel createUserManagementTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JButton createUserButton = createLargeButtonWithIcon("Create New User Account",
                new Color(255, 182, 193), "create_user.png");
        createUserButton.setPreferredSize(new Dimension(400, 100));
        createUserButton.addActionListener(e -> showCreateUserDialog());

        centerPanel.add(createUserButton);

        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton generateButton = createButton("Generate Report", new Color(144, 238, 144));
        JButton exportButton = createButton("Export to File", new Color(173, 216, 230));
        JButton clearButton = createButton("Clear", new Color(255, 222, 173));

        generateButton.addActionListener(e -> {
            reportArea.setText(generateInventoryReport());
        });

        exportButton.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog(this,
                    "Enter filename:", "inventory_report.txt");
            if (filename != null && !filename.trim().isEmpty()) {
                system.exportReport(filename);
                JOptionPane.showMessageDialog(this, "Report exported successfully!");
            }
        });

        clearButton.addActionListener(e -> reportArea.setText(""));

        buttonPanel.add(generateButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(clearButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(170, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        return button;
    }

    private JButton createLargeButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(280, 100));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        return button;
    }

    /**
     * Load an icon from the resources folder
     * @param iconName Name of the icon file (e.g., "add_cupcake.png")
     * @param size Desired size of the icon (will be scaled to size x size)
     * @return ImageIcon or null if not found
     */
    private ImageIcon loadIcon(String iconName, int size) {
        try {
            // Try loading from resources folder first
            File iconFile = new File("src/resources/icons/" + iconName);
            if (iconFile.exists()) {
                Image img = ImageIO.read(iconFile);
                Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
            
            // Try alternative path (for compiled version)
            iconFile = new File("resources/icons/" + iconName);
            if (iconFile.exists()) {
                Image img = ImageIO.read(iconFile);
                Image scaledImg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        } catch (IOException e) {
            System.out.println("Could not load icon: " + iconName + " - " + e.getMessage());
        }
        return null;
    }

    /**
     * Create a large button with custom icon support
     * @param text Button text
     * @param color Background color
     * @param iconName Name of icon file (can be null for no icon)
     * @return Styled JButton with icon
     */
    private JButton createLargeButtonWithIcon(String text, Color color, String iconName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(280, 100));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Try to load and set icon
        if (iconName != null && !iconName.isEmpty()) {
            ImageIcon icon = loadIcon(iconName, 48);
            if (icon != null) {
                button.setIcon(icon);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setIconTextGap(5);
            }
        }

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void loadCupcakeData() {
        tableModel.setRowCount(0);
        List<Cupcake> cupcakes = system.getCupcakes();

        for (Cupcake cupcake : cupcakes) {
            Object[] row = {
                    cupcake.getCupcakeId(),
                    cupcake.getName(),
                    cupcake.getCategory(),
                    cupcake.getFlavor(),
                    String.format("$%.2f", cupcake.getPrice()),
                    cupcake.getStockLevel(),
                    cupcake.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    private void showAddCupcakeDialog() {
        JDialog dialog = new JDialog(this, "Add New Cupcake", true);
        dialog.setSize(450, 550);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JComboBox<String> categoryCombo = new JComboBox<>();
        for (Category cat : system.getCategories()) {
            categoryCombo.addItem(cat.getCategoryName());
        }
        JTextField flavorField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField descField = new JTextField();

        formPanel.add(new JLabel("Cupcake ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryCombo);
        formPanel.add(new JLabel("Flavor:"));
        formPanel.add(flavorField);
        formPanel.add(new JLabel("Price ($):"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Stock Level:"));
        formPanel.add(stockField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setBackground(new Color(144, 238, 144));
        cancelButton.setBackground(new Color(255, 160, 122));

        saveButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String category = (String) categoryCombo.getSelectedItem();
                String flavor = flavorField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());
                String desc = descField.getText().trim();

                if (id.isEmpty() || name.isEmpty() || flavor.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                            "Please fill all required fields!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cupcake cupcake = new Cupcake(id, name, category, price, stock, flavor, desc);
                system.addNewCupcake(cupcake);

                JOptionPane.showMessageDialog(dialog,
                        "Cupcake added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadCupcakeData();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Invalid price or stock value!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void showUpdateStockDialog() {
        String cupcakeId = JOptionPane.showInputDialog(this, "Enter Cupcake ID:");

        if (cupcakeId != null && !cupcakeId.trim().isEmpty()) {
            Cupcake cupcake = system.findCupcakeById(cupcakeId);

            if (cupcake != null) {
                String currentStock = "Current stock: " + cupcake.getStockLevel();
                String newStockStr = JOptionPane.showInputDialog(this,
                        currentStock + "\nEnter new stock level:");

                if (newStockStr != null) {
                    try {
                        int newStock = Integer.parseInt(newStockStr);
                        system.updateCupcakeStock(cupcakeId, newStock);
                        JOptionPane.showMessageDialog(this, "Stock updated successfully!");
                        loadCupcakeData();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid stock value!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cupcake not found!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showRemoveCupcakeDialog() {
        String cupcakeId = JOptionPane.showInputDialog(this, "Enter Cupcake ID to remove:");

        if (cupcakeId != null && !cupcakeId.trim().isEmpty()) {
            Cupcake cupcake = system.findCupcakeById(cupcakeId);

            if (cupcake != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Remove cupcake: " + cupcake.getName() + "?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    system.removeCupcake(cupcakeId);
                    JOptionPane.showMessageDialog(this, "Cupcake removed successfully!");
                    loadCupcakeData();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cupcake not found!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAddCategoryDialog() {
        JDialog dialog = new JDialog(this, "Add New Category", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();

        formPanel.add(new JLabel("Category ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Category Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields!");
                return;
            }

            Category category = new Category(id, name, desc);
            system.addCategory(category);

            JOptionPane.showMessageDialog(dialog, "Category added successfully!");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showCreateUserDialog() {
        JDialog dialog = new JDialog(this, "Create New User Account", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Cashier", "Manager"});

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Account Type:"));
        formPanel.add(typeCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");

        createButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String type = (String) typeCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields!");
                return;
            }

            system.createNewAccount(username, password, type);
            JOptionPane.showMessageDialog(dialog, "User account created successfully!");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showSearchByCategoryDialog() {
        String[] categories = system.getCategories().stream()
                .map(Category::getCategoryName)
                .toArray(String[]::new);

        String category = (String) JOptionPane.showInputDialog(
                this,
                "Select Category:",
                "Search by Category",
                JOptionPane.QUESTION_MESSAGE,
                null,
                categories,
                categories[0]
        );

        if (category != null) {
            tableModel.setRowCount(0);
            List<Cupcake> results = system.getCurrentUser().searchByCategory(
                    system.getCupcakes(), category
            );

            for (Cupcake cupcake : results) {
                Object[] row = {
                        cupcake.getCupcakeId(),
                        cupcake.getName(),
                        cupcake.getCategory(),
                        cupcake.getFlavor(),
                        String.format("$%.2f", cupcake.getPrice()),
                        cupcake.getStockLevel(),
                        cupcake.getDescription()
                };
                tableModel.addRow(row);
            }

            JOptionPane.showMessageDialog(this,
                    "Found " + results.size() + " cupcake(s)");
        }
    }

    private void showSearchByNameDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter cupcake name:");

        if (name != null && !name.trim().isEmpty()) {
            tableModel.setRowCount(0);
            List<Cupcake> results = system.getCurrentUser().searchByName(
                    system.getCupcakes(), name
            );

            for (Cupcake cupcake : results) {
                Object[] row = {
                        cupcake.getCupcakeId(),
                        cupcake.getName(),
                        cupcake.getCategory(),
                        cupcake.getFlavor(),
                        String.format("$%.2f", cupcake.getPrice()),
                        cupcake.getStockLevel(),
                        cupcake.getDescription()
                };
                tableModel.addRow(row);
            }

            JOptionPane.showMessageDialog(this,
                    "Found " + results.size() + " cupcake(s)");
        }
    }

    private String generateInventoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("========== INVENTORY REPORT ==========\n\n");
        report.append("Generated: ").append(new java.util.Date()).append("\n\n");

        List<Cupcake> cupcakes = system.getCupcakes();
        report.append("Total Cupcakes: ").append(cupcakes.size()).append("\n");

        int totalStock = 0;
        double totalValue = 0;

        for (Cupcake cupcake : cupcakes) {
            totalStock += cupcake.getStockLevel();
            totalValue += cupcake.getStockLevel() * cupcake.getPrice();
        }

        report.append("Total Stock: ").append(totalStock).append(" units\n");
        report.append(String.format("Total Value: $%.2f\n\n", totalValue));

        report.append("LOW STOCK ALERTS (< 10):\n");
        for (Cupcake cupcake : cupcakes) {
            if (cupcake.getStockLevel() < 10) {
                report.append("- ").append(cupcake.getName())
                        .append(" (Stock: ").append(cupcake.getStockLevel()).append(")\n");
            }
        }

        report.append("\n========================================\n");

        return report.toString();
    }

    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            system.saveAllData();
            system.logout();
            this.dispose();
            new LoginFrame(system).setVisible(true);
        }
    }
}