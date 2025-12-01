package models.utils;


import models.Project;
import models.StatusReport;
import models.Task;
import models.User;
import models.services.ProjectService;
import models.services.ReportService;
import models.services.TaskService;
import java.util.Scanner;

public class ConsoleMenu {

    private final String software = "Software";
    private final String hardware = "Hardware";

    //private static ValidationUtils = new ValidationUtils();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Project[] projects = Project.getAllProjects();
    private static ProjectService projectService = new ProjectService();
    private static TaskService taskService = new TaskService();
    private static User currentUser = null; // Track logged-in user (start with default or login)
    private static ReportService statusReport = new ReportService();


    /**
     * Main application loop.
     */
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
                    //currentUser = UserService.switchUser(); // Assume switchUser handles login/switch
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("JAVA PROJECT MANAGEMENT SYSTEM");
        System.out.println("-----------------------------");
        //System.out.println("Current User: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        System.out.println("1. Manage Projects");
        System.out.println("2. Manage Tasks");
        System.out.println("3. View Status Reports");
        System.out.println("4. Switch User");
        System.out.println("5. Exit");
    }

    private static void handleManageProjects() {
        while (true) {
            System.out.println("\n-----------------------------");
            System.out.println("PROJECT CATALOG");
            System.out.println("-----------------------------");
            System.out.println("Filter Options:");
            System.out.println("1. View All Projects");
            System.out.println("2. Software Projects Only");
            System.out.println("3. Hardware Projects Only");
            System.out.println("4. Search by Budget Range");
            int filterChoice = ValidationUtils.getValidInt("Enter Filter choice: ", 1, 4);

            Project[] filteredProjects = null;
            switch (filterChoice) {
                case 1:
                    filteredProjects = projects;
                    break;
                case 2:
                    filteredProjects = projectService.filterProject("Software");
                    break;
                case 3:
                    filteredProjects = projectService.filterProject("Hardware");
                    break;
                case 4:
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
            System.out.println("\n-----------------------------");
            System.out.println("PROJECT DETAILS: " + project.getId());
            System.out.println("-----------------------------");
            System.out.println("Project Name: " + project.getName());
            System.out.println("Type: " + project.getType());
            System.out.println("Team Size: " + project.getTeamSize());
            System.out.println("Budget: $" + project.getBudget());

            Task[] tasks = taskService.getTasksForProject(project.getId());
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
            System.out.println("Completion Rate: " + String.format("%.0f%%", completion * 100));

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

    private static void addNewTask(String projectId) {
        System.out.println("\n-----------------------------");
        System.out.println("ADD NEW TASK");
        System.out.println("-----------------------------");
        String name = ValidationUtils.getValidString("Enter task name: ");
        Status status = ValidationUtils.getValidStatus("Enter initial status (Pending/In Progress/Completed): ");
        Task newTask = taskService.addTask(projectId, name, status);
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
        boolean success = taskService.removeTask(projectID, taskId);
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
        System.out.println("\n-----------------------------");
        System.out.println("PROJECT STATUS REPORT");
        System.out.println("-----------------------------");
        System.out.printf("%-10s | %-20s | %-6s | %-9s | %-10s\n", "Project ID", "Project Name", "Tasks", "Completed", "Progress (%)");
        System.out.println("---------------------------------------------------------------");
        double totalCompletion = 0;
        int projectCount = 0;
        for (StatusReport report : reports) {
            if (report != null) {
                int totalTasks = report.getTotalTask();
                int completedTasks = report.getCompletedTasks();
                double progress = report.getCompletionPecentage();
                System.out.printf("%-10s | %-20s | %-6d | %-9d | %-10.0f%%\n", report.getProjectID(), report.getProjectName(), totalTasks, completedTasks, progress);
                totalCompletion += progress;
                projectCount++;
            }
        }
        double avgCompletion = (projectCount > 0) ? totalCompletion / projectCount : 0;
        System.out.println("Average Completion: " + String.format("%.1f%%", avgCompletion));
    }
}
