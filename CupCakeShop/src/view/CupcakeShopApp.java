package view;

import controller.CupcakeShopSystem;
import model.*;
import java.util.List;
import java.util.Scanner;


//Main application class - Entry point for The Sweet Cupcake Shop system
//Provides console-based user interface

public class CupcakeShopApp {
    
    private static CupcakeShopSystem system;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        system = new CupcakeShopSystem();
        
        displayWelcome();
        
        boolean running = true;
        while (running) {
            if (system.getCurrentUser() == null) {
                running = handleLoginMenu();
            } else {
                handleMainMenu();
            }
        }
        
        // Save data before exit
        system.saveAllData();
        scanner.close();
        System.out.println("\nThank you for using The Sweet Cupcake Shop system!");
    }
    
    // Display welcome message
    private static void displayWelcome() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║                                                        ║");
        System.out.println("║          THE SWEET CUPCAKE SHOP MANAGEMENT            ║");
        System.out.println("║                     SYSTEM                             ║");
        System.out.println("║                                                        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    // Handle login menu
    private static boolean handleLoginMenu() {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│         LOGIN MENU                 │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│ 1. Login                           │");
        System.out.println("│ 2. Exit                            │");
        System.out.println("└────────────────────────────────────┘");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                performLogin();
                return true;
            case 2:
                return false;
            default:
                System.out.println("Invalid choice! Please try again.");
                return true;
        }
    }
    
    // Perform login
    private static void performLogin() {
        System.out.println("\n========== LOGIN ==========");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        system.login(username, password);
        
        // Display default credentials info
        if (system.getCurrentUser() == null) {
            System.out.println("\nDefault credentials:");
            System.out.println("Manager - Username: admin, Password: admin123");
            System.out.println("Cashier - Username: cashier1, Password: cashier123");
        }
    }
    
    // Handle main menu
    private static void handleMainMenu() {
        User currentUser = system.getCurrentUser();
        
        if (currentUser instanceof Manager) {
            handleManagerMenu();
        } else if (currentUser instanceof Cashier) {
            handleCashierMenu();
        }
    }
    
    // Handle cashier menu
    private static void handleCashierMenu() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         CASHIER MENU                       │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ 1. View All Cupcakes                       │");
        System.out.println("│ 2. Add New Cupcake                         │");
        System.out.println("│ 3. Search Cupcakes by Category             │");
        System.out.println("│ 4. Search Cupcakes by Name                 │");
        System.out.println("│ 5. View All Categories                     │");
        System.out.println("│ 6. Logout                                  │");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                system.viewAllCupcakes();
                break;
            case 2:
                addNewCupcakeInterface();
                break;
            case 3:
                searchByCategoryInterface();
                break;
            case 4:
                searchByNameInterface();
                break;
            case 5:
                system.displayCategories();
                break;
            case 6:
                system.logout();
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
    
    // Handle manager menu
    private static void handleManagerMenu() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│         MANAGER MENU                       │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ 1. View All Cupcakes                       │");
        System.out.println("│ 2. Add New Cupcake                         │");
        System.out.println("│ 3. Search Cupcakes by Category             │");
        System.out.println("│ 4. Search Cupcakes by Name                 │");
        System.out.println("│ 5. Update Cupcake Stock                    │");
        System.out.println("│ 6. Remove Cupcake                          │");
        System.out.println("│ 7. Create New User Account                 │");
        System.out.println("│ 8. Add New Category                        │");
        System.out.println("│ 9. View All Categories                     │");
        System.out.println("│ 10. Generate Inventory Report              │");
        System.out.println("│ 11. Export Report to File                  │");
        System.out.println("│ 12. Save Data                              │");
        System.out.println("│ 13. Logout                                 │");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                system.viewAllCupcakes();
                break;
            case 2:
                addNewCupcakeInterface();
                break;
            case 3:
                searchByCategoryInterface();
                break;
            case 4:
                searchByNameInterface();
                break;
            case 5:
                updateStockInterface();
                break;
            case 6:
                removeCupcakeInterface();
                break;
            case 7:
                createAccountInterface();
                break;
            case 8:
                addCategoryInterface();
                break;
            case 9:
                system.displayCategories();
                break;
            case 10:
                system.generateInventoryReport();
                break;
            case 11:
                exportReportInterface();
                break;
            case 12:
                system.saveAllData();
                break;
            case 13:
                system.logout();
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }
    
    // Add new cupcake interface
    private static void addNewCupcakeInterface() {
        System.out.println("\n========== Add New Cupcake ==========");
        
        System.out.print("Enter Cupcake ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        System.out.print("Enter Flavor: ");
        String flavor = scanner.nextLine();
        
        System.out.print("Enter Price: $");
        double price = getDoubleInput();
        
        System.out.print("Enter Stock Level: ");
        int stock = getIntInput();
        
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        
        Cupcake cupcake = new Cupcake(id, name, category, price, stock, flavor, description);
        system.addNewCupcake(cupcake);
    }
    
    // Search by category interface
    private static void searchByCategoryInterface() {
        System.out.println("\n========== Search by Category ==========");
        system.displayCategories();
        System.out.print("\nEnter category name: ");
        String category = scanner.nextLine();
        system.searchByCategory(category);
    }
    
    // Search by name interface
    private static void searchByNameInterface() {
        System.out.println("\n========== Search by Name ==========");
        System.out.print("Enter cupcake name (or part of it): ");
        String name = scanner.nextLine();
        system.searchByName(name);
    }
    
    // Update stock interface
    private static void updateStockInterface() {
        System.out.println("\n========== Update Cupcake Stock ==========");
        system.viewAllCupcakes();
        System.out.print("\nEnter Cupcake ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter new stock level: ");
        int stock = getIntInput();
        system.updateCupcakeStock(id, stock);
    }
    
    // Remove cupcake interface
    private static void removeCupcakeInterface() {
        System.out.println("\n========== Remove Cupcake ==========");
        system.viewAllCupcakes();
        System.out.print("\nEnter Cupcake ID to remove: ");
        String id = scanner.nextLine();
        
        System.out.print("Are you sure you want to remove this cupcake? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            system.removeCupcake(id);
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    
    // Create account interface
    private static void createAccountInterface() {
        System.out.println("\n========== Create New Account ==========");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter account type (Cashier/Manager): ");
        String type = scanner.nextLine();
        
        system.createNewAccount(username, password, type);
    }
    
    // Add category interface
    private static void addCategoryInterface() {
        System.out.println("\n========== Add New Category ==========");
        System.out.print("Enter Category ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Category Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        
        Category category = new Category(id, name, description);
        system.addCategory(category);
    }
    
    // Export report interface
    private static void exportReportInterface() {
        System.out.println("\n========== Export Report ==========");
        System.out.print("Enter filename (e.g., inventory_report.txt): ");
        String filename = scanner.nextLine();
        system.exportReport(filename);
    }
    
    // Get integer input with error handling
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }
    
    // Get double input with error handling
    private static double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a valid number: ");
            }
        }
    }
}
