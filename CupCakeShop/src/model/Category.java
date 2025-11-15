package model;

import java.io.Serializable;

//Category class represents a cupcake category
//Demonstrates Encapsulation and Object creation

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String categoryId;
    private String categoryName;
    private String description;
    
    // Constructor
    public Category(String categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }
    
    // Default constructor
    public Category() {
        this.categoryId = "";
        this.categoryName = "";
        this.description = "";
    }
    
    // Getters and Setters
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return categoryName;
    }
    
    public void displayCategoryInfo() {
        System.out.println("Category ID: " + categoryId);
        System.out.println("Category Name: " + categoryName);
        System.out.println("Description: " + description);
    }
}
