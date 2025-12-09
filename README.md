# Java Project Management System

A **Java-based Project Management System** that enables users to manage projects and tasks, track progress, and generate status reports. The system supports **Admin** and **Regular** users with role-based permissions, robust input validation, and custom exception handling.

***

## ✅ Features

### **Project Management**

*   Add new projects (Software or Hardware)
*   Filter projects by type or budget range
*   View detailed project information

### **Task Management**

*   Add, update, and remove tasks
*   Track task completion status
*   Validate task IDs and statuses

### **User Management**

*   Switch between Admin and Regular users
*   Role-based permissions
*   Simplified login functionality

### **Reporting**

*   Generate project status reports
*   Calculate completion percentage and average completion
*   View task details per project

### **Input Validation**

*   Handles invalid input gracefully
*   Supports numeric, string, status, and ID validation

### **Exception Handling**

*   Custom exceptions for tasks, projects, and invalid inputs

***

## 🏗 Architecture

The system is organized into the following **packages**:

    models/
      Project (abstract): Base class for all projects
      SoftwareProject / HardwareProject: Specialized project types
      Task: Represents individual tasks
      User (abstract): Base class for users
      AdminUser / RegularUser: Role-specific implementations
      StatusReport: Holds reporting data

    interfaces/
      Completable: Implemented by Task to check completion

    services/
      ProjectService: CRUD for projects
      TaskService: CRUD for tasks and status updates
      ReportService: Generates reports and metrics
      UserService: Handles user switching

    utils/
      ValidationUtils: Input validation helpers
      Status: Enum for task statuses (PENDING, IN_PROGRESS, COMPLETED)
      exceptions/: Custom exception classes
        - InvalidInputException
        - TaskNotFoundException
        - ProjectNotFoundException
        - EmptyProjectException
        - ProjectNotCreatedException

***

## 📊 UML Diagram Overview

The design is represented in a **class diagram**:

*   **Inheritance**
    *   `SoftwareProject` & `HardwareProject` → `Project`
    *   `AdminUser` & `RegularUser` → `User`
    *   `Task` implements `Completable`

*   **Composition / Aggregation**
    *   `Project` contains multiple `Task` objects
    *   `AdminUser` contains `TaskService` and `ProjectService`
    *   `ReportService` contains `TaskService`

*   **Dependencies**
    *   `ConsoleMenu` depends on services, utils, models, enums, and exceptions
    *   Validation methods throw/catch `InvalidInputException`
    *   Services throw appropriate custom exceptions for robustness

***

## ▶ Running the System

### **Compile & Run**

```bash
# Compile all Java files
javac -d out $(find src -name "*.java")

# Run the main entry point
java -cp out ConsoleMenu
```

### **Usage Flow**

*   Manage projects (add, filter, view)
*   Add, update, or remove tasks
*   View status reports
*   Switch between Admin and Regular users

***

## 🧪 Example Usage

    JAVA PROJECT MANAGEMENT SYSTEM
    Current User: Jacob Quaye (Admin)

    1. Manage Projects
    2. Manage Tasks
    3. View Status Reports
    4. Switch User
    5. Exit
    Enter your choice:

*   Admin can add a software project, define tasks, and update task status.
*   Regular users can view project reports and track completion percentages.

***

## ⚠ Exception Handling

*   All inputs validated via `ValidationUtils`
*   Common errors trigger **custom exceptions**:
    *   Invalid ID → `InvalidInputException`
    *   Empty project → `EmptyProjectException`
    *   Missing task → `TaskNotFoundException`

Exceptions are caught in **services** and **ConsoleMenu** for user-friendly messages.

***

## 🔮 Future Improvements

*   Add **persistent storage** (database) instead of in-memory arrays
*   Implement **GUI** for better usability
*   Integrate **authentication** with passwords and role-based access control
*   Support **multi-user sessions**


***

## 🙌 Author

**Jacob Quaye**

***
