package utils;


import models.*;
import services.ProjectService;
import services.ReportService;
import services.TaskService;
import services.UserService;

public class ConsoleMenu {

    private static ProjectService projectService = new ProjectService();
    private static TaskService taskService = new TaskService();
    private static UserService userService = new UserService();
    private static User currentUser =  new AdminUser("Jacob Quaye", "kofimave@gmail.com" );
    private static ReportService statusReport = new ReportService();


    public static void run() {
        while (true) {
            displayMainMenu();
            int choice = ValidationUtils.getValidInt("Enter your choice: ", 1, 5);
            switch (choice) {
                case 1:
                    handleManageProjects();
                    break;
                case 2:
                    handleManageTasks();
                    break;
                case 3:
                    handleViewStatusReports();
                    break;
                case 4:
                    currentUser = userService.switchUser(currentUser);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
            }
        }
    }

    private static void displayMainMenu() {
        printHeader("JAVA PROJECT MANAGEMENT SYSTEM");
        System.out.println("Current User: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        System.out.println("1. Manage Projects");
        System.out.println("2. Manage Tasks");
        System.out.println("3. View Status Reports");
        System.out.println("4. Switch User");
        System.out.println("5. Exit");
    }

    private static void handleManageProjects() {
        while (true) {
            printHeader("PROJECT CATALOG");
            System.out.println("Filter Options: ");
            System.out.println("1. View All Projects " +"(" + Project.projectLenght() + ")");
            System.out.println("2. Add Project");
            System.out.println("3. Software Projects Only");
            System.out.println("4. Hardware Projects Only");
            System.out.println("5. Search by Budget Range");
            int filterChoice = ValidationUtils.getValidInt("Enter Filter choice: ", 1, 4);

            Project[] filteredProjects = null;
            switch (filterChoice) {
                case 1:
                    filteredProjects = Project.getAllProjects();
                    break;
                case 2:
                    String type = ValidationUtils.getValidType("Enter Project type (Hardware/Software): ");
                    Project newProject = createProject(type);
                    projectService.addProject(newProject);;
                    filteredProjects = Project.getAllProjects();
                    break;
                case 3:
                    filteredProjects = projectService.filterProject("SOFTWARE");
                    break;
                case 4:
                    filteredProjects = projectService.filterProject("HARDWARE");
                    break;
                case 5:
                    int minBudget = ValidationUtils.getValidInt("Enter min budget: ", 0);
                    int maxBudget = ValidationUtils.getValidInt("Enter max budget: ", minBudget);
                    filteredProjects = projectService.filterProject(minBudget, maxBudget);
                    break;
            }

            if (filteredProjects == null || filteredProjects.length == 0) {
                System.out.println("No projects found.");
            } else {
                System.out.printf("%-6s | %-20s | %-10s | %-8s | %-10s\n", "ID", "Project Name", "Type", "Team Size", "Budget");
                System.out.println("------------------------------------------------------------");
                for (Project project : filteredProjects) {
                    if (project != null) {
                        System.out.printf("%-6s | %-20s | %-10s | %-8d | $%-9d\n", project.getId(), project.getName(), project.getType(), project.getTeamSize(), project.getBudget());
                    }
                }
            }

            String projectId = ValidationUtils.getValidId("Enter Project ID to view details (or 0 to return): ", 'P');
            if (projectId.equals("0")) {
                return;
            }
            Project project = projectService.filterProjectBYId(projectId);
            if (project == null) {
                System.out.println("Project not found.");
                continue;
            }
            displayProjectDetails(project);
        }
    }


    private static void displayProjectDetails(Project project) {
        while (true) {
            printHeader("PROJECT DETAILS: ", project.getId());
            System.out.println("Project Name: " + project.getName());
            System.out.println("Type: " + project.getType());
            System.out.println("Team Size: " + project.getTeamSize());
            System.out.println("Budget: $" + project.getBudget());

            Task[] tasks = taskService.geProjectTasks(project.getId());
            if (tasks != null && tasks.length > 0) {
                System.out.printf("%-6s | %-20s | %-10s\n", "ID", "Task Name", "Status");
                System.out.println("---------------------------------------");
                for (Task task : tasks) {
                    if (task != null) {
                        System.out.printf("%-6s | %-20s | %-10s\n", task.getTaskID(), task.getName(), task.getStatus());
                    }
                }
            } else {
                System.out.println("No tasks assigned.");
            }

            double completion = statusReport.completionPercentage(project);
            System.out.println("Completion Rate: " + String.format("%.0f%%", completion));

            System.out.println("Options:");
            System.out.println("1. Add New Task");
            System.out.println("2. Update Task Status");
            System.out.println("3. Remove Task");
            System.out.println("4. Back to Main Menu");
            int choice = ValidationUtils.getValidInt("Enter your choice: ", 1, 4);

            switch (choice) {
                case 1:
                    addNewTask(project.getId());
                    break;
                case 2:
                    updateTaskStatus(project.getId());
                    break;
                case 3:
                    removeTask(project.getId());
                    break;
                case 4:
                    return;
            }
        }
    }


    private static Project createProject(String type) {
        String name = ValidationUtils.getValidString("Enter project name: ");
        String description = ValidationUtils.getValidString("Enter project description: ");
        int budget = ValidationUtils.getValidInt("Enter project budget: ", 1000);
        int teamSize = ValidationUtils.getValidInt("Enter team size: ", 1);
        if (type.equalsIgnoreCase("Hardware")) {
            String component = ValidationUtils.getValidString("Enter component: ");
            int weight = ValidationUtils.getValidInt("Enter weight: ", 1.0f);
            return new HardwareProject(name, description, budget, teamSize, component, weight);
        } else {
            String technology = ValidationUtils.getValidString("Enter technology: ");
            String domain = ValidationUtils.getValidString("Enter domain: ");
            String versioning = ValidationUtils.getValidString("Enter version control tool: ");
            return new SoftwareProject(name, description, budget, teamSize, technology, domain, versioning);
        }
    }


    private static void addNewTask(String projectId) {
        printHeader(("ADD NEW TASK"));
        String name = ValidationUtils.getValidString("Enter task name: ");
        Status status = ValidationUtils.getValidStatus("Enter initial status (Pending/In Progress/Completed): ");
        Task newTask = taskService.addTaskToProject(projectId, name, status);
        if (newTask != null) {
            System.out.println("Task \"" + name + "\" added successfully to Project " + projectId + "!");
        } else {
            System.out.println("Error adding task (e.g., duplicate or limit reached).");
        }
    }

    private static void updateTaskStatus(String projectID) {
        String taskId = ValidationUtils.getValidId("Enter Task ID: ", 'T');
        Status newStatus = ValidationUtils.getValidStatus("Enter new status: ");
        boolean success = taskService.updateTaskStatus(projectID, newStatus, taskId);
        if (success) {
            System.out.println("Task \"" + taskId + "\" marked as " + newStatus + ".");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void removeTask(String projectID) {
        String taskId = ValidationUtils.getValidId("Enter Task ID to remove: ", 'T');
        boolean success = taskService.removeTask(currentUser, projectID, taskId);
        if (success) {
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void handleManageTasks() {
        System.out.println("Manage Tasks: Select a project first via Manage Projects.");
        String projectId = ValidationUtils.getValidId("Enter Project ID to view details (or 0 to return): ", 'P');
        if (projectId.equals("0")) {
            return;
        }
        Project project = projectService.filterProjectBYId(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        displayProjectDetails(project);
    }

    private static void handleViewStatusReports() {
        Project[] projects = Project.getAllProjects();
        StatusReport[] reports = statusReport.generateReport(projects);
        printHeader("PROJECT STATUS REPORT");
        System.out.printf("%-10s | %-20s | %-6s | %-9s | %-10s\n", "Project ID", "Project Name", "Tasks", "Completed", "Progress(%)");
        System.out.println("---------------------------------------------------------------");
        for (StatusReport report : reports) {
            if (report != null) {
                System.out.printf("%-10s | %-20s | %-6d | %-9d | %1.0f%%\n",
                        report.getProjectID(),
                        report.getProjectName(),
                        report.getTotalTask(),
                        report.getCompletedTasks(),
                        report.getCompletionPecentage());
            }
        }
        double avgCompletion = statusReport.completionAverage(projects);
        System.out.println("Average Completion: " + String.format("%.2f%%", avgCompletion));
    }

    public static void printHeader(String title, String data) {
        int padding = 2;
        int width = title.length() + data.length() + padding * 2;

        System.out.printf("%s%s%s%n", "╔", "═".repeat(width), "╗");

        int left = (width - title.length()) / 2;
        int right = width - title.length() - left;

        System.out.printf("%s%s%s%s%s%s%n", "║", " ".repeat(left), title, data, " ".repeat(right), "║");

        System.out.printf("%s%s%s%n", "╚", "═".repeat(width), "╝");
    }

    public static void printHeader(String title) {
        int padding = 2;
        int width = title.length() + padding * 2;

        System.out.printf("%s%s%s%n", "╔", "═".repeat(width), "╗");

        int left = (width - title.length()) / 2;
        int right = width - title.length() - left;

        System.out.printf("%s%s%s%s%s%n", "║", " ".repeat(left), title, " ".repeat(right), "║");

        System.out.printf("%s%s%s%n", "╚", "═".repeat(width), "╝");
    }
}
