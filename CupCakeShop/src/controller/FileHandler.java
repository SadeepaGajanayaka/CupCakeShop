package controller;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//FileHandler class manages file operations for data persistence
//Handles reading and writing data to files
public class FileHandler {
    
    private static final String CUPCAKES_FILE = "cupcakes.dat";
    private static final String USERS_FILE = "users.dat";
    private static final String CATEGORIES_FILE = "categories.dat";
    
    // Save cupcakes to file
    public static void saveCupcakes(List<Cupcake> cupcakes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUPCAKES_FILE))) {
            oos.writeObject(cupcakes);
            System.out.println("Cupcakes data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving cupcakes: " + e.getMessage());
        }
    }
    
    // Load cupcakes from file
    @SuppressWarnings("unchecked")
    public static List<Cupcake> loadCupcakes() {
        List<Cupcake> cupcakes = new ArrayList<>();
        File file = new File(CUPCAKES_FILE);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                cupcakes = (List<Cupcake>) ois.readObject();
                System.out.println("Cupcakes data loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading cupcakes: " + e.getMessage());
            }
        } else {
            System.out.println("No existing cupcakes data found. Starting with empty list.");
            // Initialize with sample data
            cupcakes = initializeSampleCupcakes();
        }
        
        return cupcakes;
    }
    
    // Save users to file
    public static void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
            System.out.println("Users data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    // Load users from file
    @SuppressWarnings("unchecked")
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (List<User>) ois.readObject();
                System.out.println("Users data loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading users: " + e.getMessage());
            }
        } else {
            System.out.println("No existing users data found. Creating default accounts.");
            // Initialize with default users
            users = initializeDefaultUsers();
        }
        
        return users;
    }
    
    // Save categories to file
    public static void saveCategories(List<Category> categories) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CATEGORIES_FILE))) {
            oos.writeObject(categories);
            System.out.println("Categories data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving categories: " + e.getMessage());
        }
    }
    
    // Load categories from file
    @SuppressWarnings("unchecked")
    public static List<Category> loadCategories() {
        List<Category> categories = new ArrayList<>();
        File file = new File(CATEGORIES_FILE);
        
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                categories = (List<Category>) ois.readObject();
                System.out.println("Categories data loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading categories: " + e.getMessage());
            }
        } else {
            System.out.println("No existing categories data found. Creating default categories.");
            categories = initializeDefaultCategories();
        }
        
        return categories;
    }
    
    // Initialize sample cupcakes
    private static List<Cupcake> initializeSampleCupcakes() {
        List<Cupcake> cupcakes = new ArrayList<>();
        
        cupcakes.add(new Cupcake("CP001", "Classic Vanilla", "Classic Flavors", 2.50, 50, 
                                "Vanilla", "Soft vanilla cupcake with vanilla buttercream"));
        cupcakes.add(new Cupcake("CP002", "Rich Chocolate", "Classic Flavors", 2.75, 45, 
                                "Chocolate", "Moist chocolate cupcake with chocolate ganache"));
        cupcakes.add(new Cupcake("CP003", "Red Velvet Delight", "Classic Flavors", 3.00, 40, 
                                "Red Velvet", "Classic red velvet with cream cheese frosting"));
        cupcakes.add(new Cupcake("CP004", "Strawberry Dream", "Fruity Delights", 3.25, 35, 
                                "Strawberry", "Fresh strawberry cupcake with strawberry frosting"));
        cupcakes.add(new Cupcake("CP005", "Lemon Burst", "Fruity Delights", 3.00, 30, 
                                "Lemon", "Tangy lemon cupcake with lemon cream"));
        cupcakes.add(new Cupcake("CP006", "Pumpkin Spice", "Seasonal Specials", 3.50, 25, 
                                "Pumpkin", "Seasonal pumpkin spice with cream cheese frosting"));
        cupcakes.add(new Cupcake("CP007", "Gluten-Free Chocolate", "Gluten-Free", 3.75, 20, 
                                "Chocolate", "Delicious gluten-free chocolate cupcake"));
        cupcakes.add(new Cupcake("CP008", "Vegan Vanilla", "Gluten-Free", 3.50, 20, 
                                "Vanilla", "Plant-based vanilla cupcake"));
        
        return cupcakes;
    }
    
    // Initialize default users
    private static List<User> initializeDefaultUsers() {
        List<User> users = new ArrayList<>();
        
        users.add(new Manager("admin", "admin123"));
        users.add(new Cashier("cashier1", "cashier123"));
        
        return users;
    }
    
    // Initialize default categories
    private static List<Category> initializeDefaultCategories() {
        List<Category> categories = new ArrayList<>();
        
        categories.add(new Category("CAT001", "Classic Flavors", 
                                   "Traditional cupcake flavors loved by everyone"));
        categories.add(new Category("CAT002", "Fruity Delights", 
                                   "Fresh fruit-flavored cupcakes"));
        categories.add(new Category("CAT003", "Seasonal Specials", 
                                   "Limited edition seasonal flavors"));
        categories.add(new Category("CAT004", "Gluten-Free", 
                                   "Gluten-free and dietary-friendly options"));
        categories.add(new Category("CAT005", "Custom Orders", 
                                   "Personalized cupcakes for special occasions"));
        
        return categories;
    }
    
    // Export cupcakes to text file for reports
    public static void exportCupcakesToText(List<Cupcake> cupcakes, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("========== The Sweet Cupcake Shop - Inventory Report ==========");
            writer.println("Generated on: " + new java.util.Date());
            writer.println("================================================================\n");
            
            for (Cupcake cupcake : cupcakes) {
                writer.println("Cupcake ID: " + cupcake.getCupcakeId());
                writer.println("Name: " + cupcake.getName());
                writer.println("Category: " + cupcake.getCategory());
                writer.println("Flavor: " + cupcake.getFlavor());
                writer.println("Price: $" + cupcake.getPrice());
                writer.println("Stock Level: " + cupcake.getStockLevel());
                writer.println("Description: " + cupcake.getDescription());
                writer.println("-----------------------------------\n");
            }
            
            System.out.println("Report exported successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Error exporting report: " + e.getMessage());
        }
    }
}
