# CupCakeShop
Java desktop application for cupcake shop management with role-based access, inventory tracking, and GUI interface. Demonstrates OOP principles including inheritance, polymorphism, and MVC architecture using Java Swing.

# The Sweet Cupcake Shop Management System

A comprehensive Java-based desktop application for managing a cupcake shop's inventory, users, and operations. Built using Java Swing for the GUI and implementing core Object-Oriented Programming principles.

## 🧁 Overview

The Sweet Cupcake Shop Management System is a desktop application designed to streamline cupcake shop operations. It features role-based access control with separate interfaces for managers and cashiers, complete inventory management, and persistent data storage.

## ✨ Features

### Core Functionality
- **User Authentication**: Secure login system with role-based access
- **Inventory Management**: Complete cupcake catalog with categories, pricing, and stock levels
- **Role-Based Dashboards**: Separate interfaces for Managers and Cashiers
- **Data Persistence**: Automatic saving and loading of all data
- **Search & Filter**: Search cupcakes by name, category, and other criteria

### Manager Features
- Create and manage user accounts (Cashier/Manager)
- Add, update, and remove cupcakes
- Update stock levels and pricing
- Generate comprehensive inventory reports
- Export reports to text files
- Manage product categories
- View low-stock alerts

### Cashier Features
- View complete cupcake inventory
- Add new cupcakes to inventory
- Search products by category and name
- Process sales transactions
- Update stock after sales

## 🏗️ Architecture

### Design Patterns & OOP Concepts

**1. Model-View-Controller (MVC)**
- `model/` - Data models and business logic
- `view/gui/` - Swing-based user interface
- `controller/` - System logic and data management

**2. Inheritance**
- Abstract `User` class with concrete `Manager` and `Cashier` implementations
- Demonstrates polymorphism through method overriding

**3. Encapsulation**
- Private fields with public getters/setters
- Data validation and controlled access

**4. Polymorphism**
- Method overloading in Manager class
- Abstract method implementations in User subclasses

**5. File I/O & Serialization**
- Persistent data storage using Java serialization
- Automatic backup and recovery

## 📁 Project Structure

```
src/
├── controller/
│   ├── CupcakeShopSystem.java    # Main system controller
│   └── FileHandler.java          # Data persistence manager
├── model/
│   ├── User.java                 # Abstract user base class
│   ├── Manager.java              # Manager user implementation
│   ├── Cashier.java              # Cashier user implementation
│   ├── Cupcake.java              # Cupcake data model
│   └── Category.java             # Category data model
└── view/gui/
    ├── CupcakeShopGUI.java       # Main application entry point
    ├── LoginFrame.java           # Login interface
    ├── ManagerDashboard.java     # Manager main interface
    └── CashierDashboard.java     # Cashier main interface
```

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- Java IDE (Eclipse, IntelliJ IDEA, or NetBeans)
- Basic understanding of Java and Swing

### Installation & Setup

1. **Download/Clone the Project**
   ```bash
   # If using Git
   git clone [repository-url]
   
   # Or download and extract the ZIP file
   ```

2. **Import into IDE**
   - Open your Java IDE
   - Import the project as a Java project
   - Ensure all source files are in the correct package structure

3. **Compile and Run**
   - Compile all Java files
   - Run `CupcakeShopGUI.java` as the main class

### Running the Application

1. **Launch the Application**
   ```bash
   java view.gui.CupcakeShopGUI
   ```

2. **Login with Default Credentials**
   - **Manager**: 
     - Username: `admin`
     - Password: `admin123`
   - **Cashier**: 
     - Username: `cashier1`
     - Password: `cashier123`

## 💻 User Guide

### First-Time Setup
1. Launch the application
2. Use default credentials to log in
3. The system will create sample data automatically
4. Start managing your cupcake inventory!

### Manager Workflow
1. **Login** with manager credentials
2. **View Dashboard** with complete system overview
3. **Manage Inventory**:
   - Add new cupcakes
   - Update existing products
   - Remove discontinued items
   - Monitor stock levels
4. **Generate Reports** for business insights
5. **Manage Users** - create cashier accounts

### Cashier Workflow
1. **Login** with cashier credentials
2. **View Inventory** in the main table
3. **Add Products** when restocking
4. **Search Products** for customers
5. **Process Sales** and update stock

## 🗄️ Data Management

### File Storage
The application automatically creates and manages these data files:
- `cupcakes.dat` - Cupcake inventory data
- `users.dat` - User account information
- `categories.dat` - Product categories

### Sample Data
On first run, the system initializes with:
- **8 Sample Cupcakes** across 4 categories
- **2 Default User Accounts** (1 Manager, 1 Cashier)
- **5 Product Categories** for organization

## 🔧 Technical Details

### Key Classes

**CupcakeShopSystem**
- Main controller managing all system operations
- Handles authentication and data coordination
- Provides unified interface for all features

**User Hierarchy**
- `User` (Abstract): Base authentication and common methods
- `Manager`: Extended privileges for system administration
- `Cashier`: Front-end operations and basic inventory management

**Data Models**
- `Cupcake`: Complete product information with stock management
- `Category`: Product categorization system

### GUI Components
- **Swing-based** desktop interface
- **Custom styling** with gradient backgrounds
- **Table-based** data display
- **Dialog forms** for data entry
- **Responsive layouts** for different screen sizes

## 🎯 Academic Learning Objectives

This project demonstrates:
- **Object-Oriented Programming**: Classes, inheritance, polymorphism, encapsulation
- **Design Patterns**: MVC architecture, abstract classes
- **GUI Development**: Swing components, event handling, layout management
- **File I/O**: Serialization, persistent data storage
- **Software Engineering**: Code organization, documentation, user experience

## 🚨 Troubleshooting

### Common Issues

**Application Won't Start**
- Ensure Java JDK 8+ is installed
- Check that all files are in correct package structure
- Verify main class path: `view.gui.CupcakeShopGUI`

**Login Failed**
- Use default credentials exactly as specified
- Check caps lock and typing accuracy
- Restart application if data files are corrupted

**Data Not Saving**
- Ensure write permissions in application directory
- Check available disk space
- Restart application to reload data

**GUI Display Issues**
- Update Java to latest version
- Try different look-and-feel settings
- Check screen resolution compatibility

## 📋 System Requirements

- **Operating System**: Windows, macOS, or Linux
- **Java Version**: JDK 8 or higher
- **Memory**: Minimum 512MB RAM
- **Storage**: 50MB free space
- **Display**: 1024x768 minimum resolution

## 🔮 Future Enhancements

Potential improvements could include:
- Database integration (MySQL, PostgreSQL)
- Web-based interface using Spring Boot
- Barcode scanning for inventory
- Sales analytics and reporting
- Customer management system
- Online ordering integration
- Multi-location support

## 📄 License

This project is created for educational purposes. Feel free to use and modify for learning and academic projects.

## 👨‍💻 Author

Developed as part of Object-Oriented Programming coursework, demonstrating practical application of Java concepts in a real-world business scenario.

---

**Note**: This application is designed for educational purposes and demonstrates core programming concepts. For production use, additional security measures, error handling, and testing would be recommended.
