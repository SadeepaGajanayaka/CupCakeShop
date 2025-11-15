package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//CupcakeShopSystem class manages the overall system operations
//Acts as the controller for the application
public class CupcakeShopSystem {
    
    private List<Cupcake> cupcakes;
    private List<User> users;
    private List<Category> categories;
    private User currentUser;
    private Scanner scanner;
    
    // Constructor
    public CupcakeShopSystem() {
        this.scanner = new Scanner(System.in);
        loadData();
    }
    
    // Load data from files
    private void loadData() {
        System.out.println("Loading system data...");
        this.cupcakes = FileHandler.loadCupcakes();
        this.users = FileHandler.loadUsers();
        this.categories = FileHandler.loadCategories();
        System.out.println("System data loaded successfully!\n");
    }
    
    // Save all data
    public void saveAllData() {
        System.out.println("\nSaving all data...");
        FileHandler.saveCupcakes(cupcakes);
        FileHandler.saveUsers(users);
        FileHandler.saveCategories(categories);
        System.out.println("All data saved successfully!");
    }
    
    // Login method
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.authenticate(username, password)) {
                currentUser = user;
                System.out.println("\nLogin successful!");
                System.out.println("Welcome, " + username + " (" + user.getUserType() + ")");
                return true;
            }
        }
        System.out.println("Invalid username or password!");
        return false;
    }
    
    // Logout method
    public void logout() {
        if (currentUser != null) {
            System.out.println("Logging out " + currentUser.getUsername() + "...");
            currentUser = null;
            System.out.println("Logged out successfully!");
        }
    }
    
    // Get current user
    public User getCurrentUser() {
        return currentUser;
    }
    
    // View all cupcakes
    public void viewAllCupcakes() {
        if (currentUser != null) {
            currentUser.viewCupcakeDetails(cupcakes);
        } else {
            System.out.println("Please login first!");
        }
    }
    
    // Search cupcakes by category
    public void searchByCategory(String category) {
        if (currentUser != null) {
            List<Cupcake> results = currentUser.searchByCategory(cupcakes, category);
            displaySearchResults(results, "Category: " + category);
        } else {
            System.out.println("Please login first!");
        }
    }
    
    // Search cupcakes by name
    public void searchByName(String name) {
        if (currentUser != null) {
            List<Cupcake> results = currentUser.searchByName(cupcakes, name);
            displaySearchResults(results, "Name contains: " + name);
        } else {
            System.out.println("Please login first!");
        }
    }
    
    // Display search results
    private void displaySearchResults(List<Cupcake> results, String searchCriteria) {
        System.out.println("\n========== Search Results ==========");
        System.out.println("Search: " + searchCriteria);
        System.out.println("Found " + results.size() + " result(s)\n");
        
        if (results.isEmpty()) {
            System.out.println("No cupcakes found matching your search.");
        } else {
            for (Cupcake cupcake : results) {
                System.out.println(cupcake);
                System.out.println("-----------------------------------");
            }
        }
    }
    
    // Add new cupcake (Cashier and Manager)
    public void addNewCupcake(Cupcake cupcake) {
        if (currentUser instanceof Cashier) {
            ((Cashier) currentUser).addCupcake(cupcakes, cupcake);
        } else if (currentUser instanceof Manager) {
            ((Manager) currentUser).addNewCupcake(cupcakes, cupcake);
        } else {
            System.out.println("You don't have permission to add cupcakes!");
        }
    }
    
    // Create new account (Manager only)
    public void createNewAccount(String username, String password, String accountType) {
        if (currentUser instanceof Manager) {
            ((Manager) currentUser).createNewAccount(users, username, password, accountType);
        } else {
            System.out.println("Only managers can create new accounts!");
        }
    }
    
    // Update cupcake stock (Manager only)
    public void updateCupcakeStock(String cupcakeId, int newStock) {
        if (currentUser instanceof Manager) {
            Cupcake cupcake = findCupcakeById(cupcakeId);
            if (cupcake != null) {
                ((Manager) currentUser).updateCupcakeStock(cupcake, newStock);
            } else {
                System.out.println("Cupcake not found!");
            }
        } else {
            System.out.println("Only managers can update stock!");
        }
    }
    
    // Remove cupcake (Manager only)
    public void removeCupcake(String cupcakeId) {
        if (currentUser instanceof Manager) {
            ((Manager) currentUser).removeCupcake(cupcakes, cupcakeId);
        } else {
            System.out.println("Only managers can remove cupcakes!");
        }
    }
    
    // Generate inventory report (Manager only)
    public void generateInventoryReport() {
        if (currentUser instanceof Manager) {
            ((Manager) currentUser).generateInventoryReport(cupcakes);
        } else {
            System.out.println("Only managers can generate reports!");
        }
    }
    
    // Find cupcake by ID
    public Cupcake findCupcakeById(String cupcakeId) {
        for (Cupcake cupcake : cupcakes) {
            if (cupcake.getCupcakeId().equals(cupcakeId)) {
                return cupcake;
            }
        }
        return null;
    }
    
    // Get all categories
    public List<Category> getCategories() {
        return categories;
    }
    
    // Get all cupcakes
    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }
    
    // Add category (Manager only)
    public void addCategory(Category category) {
        if (currentUser instanceof Manager) {
            categories.add(category);
            System.out.println("Category added: " + category.getCategoryName());
        } else {
            System.out.println("Only managers can add categories!");
        }
    }
    
    // Display all categories
    public void displayCategories() {
        System.out.println("\n========== Available Categories ==========");
        for (Category category : categories) {
            category.displayCategoryInfo();
            System.out.println("-----------------------------------");
        }
    }
    
    // Export report
    public void exportReport(String filename) {
        FileHandler.exportCupcakesToText(cupcakes, filename);
    }
    
    // Close scanner
    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
