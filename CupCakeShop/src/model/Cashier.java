package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Cashier class extends User - Demonstrates Inheritance
//Represents a cashier user with specific functionalities

public class Cashier extends User {
    private static final long serialVersionUID = 1L;
    
    // Constructor
    public Cashier(String username, String password) {
        super(username, password, "Cashier");
    }
    
    // Implementation of abstract method - Polymorphism
    @Override
    public void viewCupcakeDetails(List<Cupcake> cupcakes) {
        System.out.println("\n========== All Cupcakes ==========");
        if (cupcakes.isEmpty()) {
            System.out.println("No cupcakes available.");
        } else {
            for (Cupcake cupcake : cupcakes) {
                System.out.println(cupcake);
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
    
    // Method Overloading - Polymorphism
    public List<Cupcake> searchByPrice(List<Cupcake> cupcakes, double minPrice, double maxPrice) {
        List<Cupcake> results = new ArrayList<>();
        for (Cupcake cupcake : cupcakes) {
            if (cupcake.getPrice() >= minPrice && cupcake.getPrice() <= maxPrice) {
                results.add(cupcake);
            }
        }
        return results;
    }
    
    // Additional cashier-specific method
    public void addCupcake(List<Cupcake> cupcakes, Cupcake newCupcake) {
        cupcakes.add(newCupcake);
        System.out.println("Cupcake added successfully: " + newCupcake.getName());
    }
    
    // Method to process sale
    public void processSale(Cupcake cupcake, int quantity) {
        if (cupcake.getStockLevel() >= quantity) {
            cupcake.updateStock(-quantity);
            double total = cupcake.getPrice() * quantity;
            System.out.println("Sale processed: " + quantity + " x " + cupcake.getName());
            System.out.println("Total: $" + String.format("%.2f", total));
        } else {
            System.out.println("Insufficient stock for " + cupcake.getName());
        }
    }
}
