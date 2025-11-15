package view.gui;

import controller.CupcakeShopSystem;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

//CashierDashboard - Main GUI for Cashier users
public class CashierDashboard extends JFrame {
    
    private CupcakeShopSystem system;
    private JTable cupcakeTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton searchCategoryButton;
    private JButton searchNameButton;
    private JButton refreshButton;
    private JButton logoutButton;
    private JLabel welcomeLabel;
    private JPanel backgroundPanel;
    
    public CashierDashboard(CupcakeShopSystem system) {
        this.system = system;
        initializeComponents();
        setupLayout();
        setupListeners();
        loadCupcakeData();
        
        setTitle("The Sweet Cupcake Shop - Cashier Dashboard");
        setSize(1000, 600);
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
                // Gradient background
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
        // Welcome label
        User currentUser = system.getCurrentUser();
        welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + " (Cashier)");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(139, 69, 19));
        
        // Table
        String[] columnNames = {"ID", "Name", "Category", "Flavor", "Price", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        cupcakeTable = new JTable(tableModel);
        cupcakeTable.setFont(new Font("Arial", Font.PLAIN, 12));
        cupcakeTable.setRowHeight(25);
        cupcakeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        cupcakeTable.getTableHeader().setBackground(new Color(255, 182, 193));
        
        // Buttons
        addButton = createButton("Add Cupcake", new Color(144, 238, 144));
        searchCategoryButton = createButton("Search by Category", new Color(173, 216, 230));
        searchNameButton = createButton("Search by Name", new Color(173, 216, 230));
        refreshButton = createButton("Refresh", new Color(255, 222, 173));
        logoutButton = createButton("Logout", new Color(255, 160, 122));
    }
    
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(170, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Add icon based on button text
        if (text.contains("Add")) {
            button.setIcon(UIManager.getIcon("FileView.fileIcon"));
        } else if (text.contains("Search")) {
            button.setIcon(UIManager.getIcon("FileChooser.detailsViewIcon"));
        } else if (text.contains("Refresh")) {
            button.setIcon(UIManager.getIcon("FileChooser.upFolderIcon"));
        } else if (text.contains("Logout")) {
            button.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
        }
        
        return button;
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
        JLabel titleLabel = new JLabel("THE SWEET CUPCAKE SHOP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(139, 69, 19));
        titlePanel.add(titleLabel);
        
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        // Center panel (table)
        JScrollPane scrollPane = new JScrollPane(cupcakeTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 182, 193), 2),
            "Cupcake Inventory",
            0,
            0,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(searchCategoryButton);
        buttonPanel.add(searchNameButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);
        
        // Add panels to frame
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        addButton.addActionListener(e -> showAddCupcakeDialog());
        searchCategoryButton.addActionListener(e -> showSearchByCategoryDialog());
        searchNameButton.addActionListener(e -> showSearchByNameDialog());
        refreshButton.addActionListener(e -> loadCupcakeData());
        logoutButton.addActionListener(e -> performLogout());
    }
    
    private void loadCupcakeData() {
        tableModel.setRowCount(0); // Clear table
        List<Cupcake> cupcakes = system.getCupcakes();
        
        for (Cupcake cupcake : cupcakes) {
            Object[] row = {
                cupcake.getCupcakeId(),
                cupcake.getName(),
                cupcake.getCategory(),
                cupcake.getFlavor(),
                String.format("$%.2f", cupcake.getPrice()),
                cupcake.getStockLevel()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddCupcakeDialog() {
        JDialog dialog = new JDialog(this, "Add New Cupcake", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
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
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Stock:"));
        formPanel.add(stockField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
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
                    JOptionPane.showMessageDialog(dialog, "Please fill all fields!");
                    return;
                }
                
                Cupcake cupcake = new Cupcake(id, name, category, price, stock, flavor, desc);
                system.addNewCupcake(cupcake);
                
                JOptionPane.showMessageDialog(dialog, "Cupcake added successfully!");
                loadCupcakeData();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid price or stock value!");
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
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
                    cupcake.getStockLevel()
                };
                tableModel.addRow(row);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Found " + results.size() + " cupcake(s) in category: " + category);
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
                    cupcake.getStockLevel()
                };
                tableModel.addRow(row);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Found " + results.size() + " cupcake(s) matching: " + name);
        }
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
