package model;

import java.util.ArrayList;
import java.util.List;

//Manager class extends User - Demonstrates Inheritance and Polymorphism
//Represents a manager user with extended functionalities
public class Manager extends User {
    private static final long serialVersionUID = 1L;
    
    // Constructor
    public Manager(String username, String password) {
        super(username, password, "Manager");
    }
    
    // Implementation of abstract method - Polymorphism
    @Override
    public void viewCupcakeDetails(List<Cupcake> cupcakes) {
        System.out.println("\n========== All Cupcakes (Manager View) ==========");
        if (cupcakes.isEmpty()) {
            System.out.println("No cupcakes available.");
        } else {
            for (Cupcake cupcake : cupcakes) {
                cupcake.viewDetails();
                System.out.println("-----------------------------------");
            }
        }
    }
    
    // Implementation of abstract method - Polymorphism
    @Override
    public List<Cupcake> searchByCategory(List<Cupcake> cupcakes, String category) {
        List<Cupcake> results = new ArrayList<>();
        for (Cupcake cupcake : cupcakes) {
            if (cupcake.getCategory().equalsIgnoreCase(category)) {
                results.add(cupcake);
            }
        }
        return results;
    }
    
    // Implementation of abstract method - Polymorphism
    @Override
    public List<Cupcake> searchByName(List<Cupcake> cupcakes, String name) {
        List<Cupcake> results = new ArrayList<>();
        for (Cupcake cupcake : cupcakes) {
            if (cupcake.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(cupcake);
            }
        }
        return results;
    }
    
    // Manager-specific methods
    public void createNewAccount(List<User> users, String username, String password, String accountType) {
        User newUser = null;
        
        if (accountType.equalsIgnoreCase("Cashier")) {
            newUser = new Cashier(username, password);
        } else if (accountType.equalsIgnoreCase("Manager")) {
            newUser = new Manager(username, password);
        }
        
        if (newUser != null) {
            users.add(newUser);
            System.out.println("New " + accountType + " account created successfully!");
            System.out.println("Username: " + username);
        } else {
            System.out.println("Invalid account type!");
        }
    }
    
    // Method Overloading - Polymorphism
    public void addNewCupcake(List<Cupcake> cupcakes, Cupcake cupcake) {
        cupcakes.add(cupcake);
        System.out.println("New cupcake added: " + cupcake.getName());
    }
    
    // Method Overloading - Polymorphism
    public void addNewCupcake(List<Cupcake> cupcakes, String id, String name, String category, 
                             double price, int stock, String flavor, String description) {
        Cupcake cupcake = new Cupcake(id, name, category, price, stock, flavor, description);
        cupcakes.add(cupcake);
        System.out.println("New cupcake added: " + name);
    }
    
    // Update stock level
    public void updateCupcakeStock(Cupcake cupcake, int newStock) {
        int oldStock = cupcake.getStockLevel();
        cupcake.setStockLevel(newStock);
        System.out.println("Stock updated for " + cupcake.getName());
        System.out.println("Old stock: " + oldStock + " | New stock: " + newStock);
    }
    
    // Remove cupcake
    public void removeCupcake(List<Cupcake> cupcakes, String cupcakeId) {
        boolean removed = false;
        for (int i = 0; i < cupcakes.size(); i++) {
            if (cupcakes.get(i).getCupcakeId().equals(cupcakeId)) {
                Cupcake removed_cupcake = cupcakes.remove(i);
                System.out.println("Cupcake removed: " + removed_cupcake.getName());
                removed = true;
                break;
            }
        }
        if (!removed) {
            System.out.println("Cupcake with ID " + cupcakeId + " not found.");
        }
    }
    
    // Update cupcake details
    public void updateCupcakeDetails(Cupcake cupcake, String name, double price, String category) {
        cupcake.setName(name);
        cupcake.setPrice(price);
        cupcake.setCategory(category);
        System.out.println("Cupcake details updated successfully!");
    }
    
    // Generate inventory report
    public void generateInventoryReport(List<Cupcake> cupcakes) {
        System.out.println("\n========== Inventory Report ==========");
        System.out.println("Total cupcakes: " + cupcakes.size());
        
        int totalStock = 0;
        double totalValue = 0;
        
        for (Cupcake cupcake : cupcakes) {
            totalStock += cupcake.getStockLevel();
            totalValue += cupcake.getStockLevel() * cupcake.getPrice();
        }
        
        System.out.println("Total stock quantity: " + totalStock);
        System.out.println("Total inventory value: $" + String.format("%.2f", totalValue));
        
        // Low stock alert
        System.out.println("\nLow Stock Alerts (Stock < 10):");
        for (Cupcake cupcake : cupcakes) {
            if (cupcake.getStockLevel() < 10) {
                System.out.println("- " + cupcake.getName() + " (Stock: " + cupcake.getStockLevel() + ")");
            }
        }
    }
}
