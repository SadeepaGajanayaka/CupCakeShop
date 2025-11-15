package model;

import java.io.Serializable;

//Cupcake class represents a cupcake product in the store
//Demonstrates Encapsulation through private fields and public getters/setters

public class Cupcake implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Private fields - Encapsulation
    private String cupcakeId;
    private String name;
    private String category;
    private double price;
    private int stockLevel;
    private String flavor;
    private String description;
    
    // Constructor
    public Cupcake(String cupcakeId, String name, String category, double price, 
                   int stockLevel, String flavor, String description) {
        this.cupcakeId = cupcakeId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockLevel = stockLevel;
        this.flavor = flavor;
        this.description = description;
    }
    
    // Default constructor
    public Cupcake() {
        this.cupcakeId = "";
        this.name = "";
        this.category = "";
        this.price = 0.0;
        this.stockLevel = 0;
        this.flavor = "";
        this.description = "";
    }
    
    // Getters and Setters - Encapsulation
    public String getCupcakeId() {
        return cupcakeId;
    }
    
    public void setCupcakeId(String cupcakeId) {
        this.cupcakeId = cupcakeId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getStockLevel() {
        return stockLevel;
    }
    
    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
    
    public String getFlavor() {
        return flavor;
    }
    
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    // Method to view cupcake details
    public void viewDetails() {
        System.out.println("Cupcake ID: " + cupcakeId);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Flavor: " + flavor);
        System.out.println("Price: $" + price);
        System.out.println("Stock Level: " + stockLevel);
        System.out.println("Description: " + description);
    }
    
    // Method to update stock
    public void updateStock(int quantity) {
        this.stockLevel += quantity;
    }
    
    // Method to check if in stock
    public boolean isInStock() {
        return stockLevel > 0;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Category: %s | Flavor: %s | Price: $%.2f | Stock: %d", 
                           cupcakeId, name, category, flavor, price, stockLevel);
    }
}
