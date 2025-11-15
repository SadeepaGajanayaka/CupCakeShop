package model;

import java.io.Serializable;
import java.util.List;

//Abstract User class - Demonstrates Abstraction
//Base class for different types of users in the system
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Protected fields - accessible by subclasses
    protected String username;
    protected String password;
    protected String userType;
    
    // Constructor
    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    // Abstract methods - must be implemented by subclasses
    public abstract void viewCupcakeDetails(List<Cupcake> cupcakes);
    public abstract List<Cupcake> searchByCategory(List<Cupcake> cupcakes, String category);
    public abstract List<Cupcake> searchByName(List<Cupcake> cupcakes, String name);
    
    // Concrete method - available to all subclasses
    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    @Override
    public String toString() {
        return "Username: " + username + " | User Type: " + userType;
    }
}
