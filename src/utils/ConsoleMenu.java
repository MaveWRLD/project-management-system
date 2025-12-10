package utils;


import models.*;
import services.ProjectService;
import services.ReportService;
import services.TaskService;
import services.UserService;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectsNotCreatedException;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;

public class ConsoleMenu {

    private ProjectService projectService;
    private TaskService taskService;
    private ReportService statusReport;
    private UserService userService;

    private User currentUser;

    public ConsoleMenu(ProjectService projectService, TaskService taskService, ReportService statusReport, UserService userService, User currentUser) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.statusReport = statusReport;
        this.userService = userService;
        this.currentUser = currentUser;
    }


    public void run() {
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

    private void displayMainMenu() {
        printHeader("JAVA PROJECT MANAGEMENT SYSTEM");
        System.out.println("Current User: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        String[] options = {"1. Manage Projects", "2. Manage Tasks", "3. View Status Reports", "4. Switch User", "5. Exit"};
        printText(options);
    }

    private void handleManageProjects() {
        Project[] projects = new Project[0];
        try {
            projects = projectService.allProjects();
        } catch (ProjectsNotCreatedException e) {
            System.out.println(e.getMessage());
        }
        while (true) {
            printHeader("PROJECT CATALOG");
            String[] options = {"Filter Options: ",
                    "1. View All Projects " +"(" + ResizeUtils.countElements(projects) + ")",
                    "2. Add Project",
                    "3. Software Projects Only",
                    "4. Hardware Projects Only",
                    "5. Search by Budget Range",
            };
            printText(options);
            try {
                Project[] filteredProjects = getFilteredProjects();
                System.out.printf("%-6s | %-20s | %-10s | %-8s | %-10s\n", "ID", "Project Name", "Type", "Team Size", "Budget");
                System.out.println("------------------------------------------------------------");
                for (Project project : filteredProjects) {
                if (project != null) {
                    System.out.printf("%-6s | %-20s | %-10s | %-8d | $%-9d\n", project.getId(), project.getName(), project.getType(), project.getTeamSize(), project.getBudget());
                }
                }
            if (handleManageTasks()) return;
            } catch (ProjectsNotCreatedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Project[] getFilteredProjects() throws ProjectsNotCreatedException {
        int filterChoice = ValidationUtils.getValidInt("Enter Filter choice: ", 1, 5);

        Project[] filteredProjects = null;
        switch (filterChoice) {
            case 1:
                filteredProjects = projectService.allProjects();
                break;
            case 2:
                String type = ValidationUtils.getValidType("Enter Project type (Hardware/Software): ");
                Project newProject = createProject(type);
                projectService.addProject(newProject);
                filteredProjects = projectService.allProjects();
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
        return filteredProjects;
    }

    private void displayProjectDetails(Project project) {
        while (true) {
            printHeader("PROJECT DETAILS: ", project.getId());
            String[] detailsProject = {"Project Name: " + project.getName(), "Type: " + project.getType(), "Team Size: " + project.getTeamSize(),
                    "Budget: $" + project.getBudget()};
            printText(detailsProject);

            try {
                Task[] tasks = taskService.getProjectTasks(project.getId());

                System.out.printf("%-6s | %-20s | %-10s\n", "ID", "Task Name", "Status");
                System.out.println("---------------------------------------");
                for (Task task : tasks) {
                    if (task != null) {
                        System.out.printf("%-6s | %-20s | %-10s\n", task.getTaskID(), task.getName(), task.getStatus());
                    }
                }

                double completion = statusReport.completionPercentage(project.getId());
                System.out.println("Completion Rate: " + String.format("%.0f%%", completion));
            } catch (EmptyProjectException e) {
                System.out.println(e.getMessage());
            }

            if (taskDetailsPrompt(project)) return;
        }
    }

    private boolean taskDetailsPrompt(Project project) {
        String[] options = {"Options:", "1. Add New Task", "2. Update Task Status", "3. Remove Task", "4. Back to Main Menu"};
        printText(options);
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
                return true;
        }
        return false;
    }

    private static Project createProject(String projectType) {
        String name = ValidationUtils.getValidString("Enter project name: ");
        String description = ValidationUtils.getValidString("Enter project description: ");
        int budget = ValidationUtils.getValidInt("Enter project budget: ", 1000);
        int teamSize = ValidationUtils.getValidInt("Enter team size: ", 1);
        if (projectType.equalsIgnoreCase("Hardware")) {
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


    private void addNewTask(String projectId) {
        printHeader(("ADD NEW TASK"));
        String name = ValidationUtils.getValidString("Enter task name: ");
        Status status = ValidationUtils.getValidStatus("Enter initial status (Pending/In Progress/Completed): ");
        taskService.addTaskToProject(projectId, name, status);
    }

    private void updateTaskStatus(String projectID) {
        String taskId = ValidationUtils.getValidId("Enter Task ID: ", 'T');
        Status newStatus = ValidationUtils.getValidStatus("Enter new status: ");
        try{
            taskService.updateTaskStatus(projectID, newStatus, taskId);
            System.out.println("Task \"" + taskId + "\" marked as " + newStatus + ".");
        } catch (TaskNotFoundException | EmptyProjectException | ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeTask(String projectID) {
        String taskId = ValidationUtils.getValidId("Enter Task ID to remove: ", 'T');
        try {
            currentUser.removeTask(projectID, taskId);
            System.out.println("Task removed successfully.");
        } catch (UnsupportedOperationException | TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean handleManageTasks() {
        String projectId = ValidationUtils.getValidId("Enter Project ID to view details (or 0 to return): ", 'P');
        if (projectId.equals("0")) {
            return true;
        }
        Project project;
        try {
            project = projectService.filterProjectBYId(projectId);
            displayProjectDetails(project);
        } catch (ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void handleViewStatusReports() {
        Project[] projects = null;
        try {
            projects = projectService.allProjects();
        } catch (ProjectsNotCreatedException e) {
            System.out.println(e.getMessage());
        }
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
                        report.getCompletionPercentage());
            }
        }
        double avgCompletion = statusReport.completionAverage(projects);
        System.out.println("Average Completion: " + String.format("%.2f%%", avgCompletion));
    }

    public static void printHeader(String title, String data) {
        int padding = 2;
        int width = title.length() + data.length() + padding * 2;
        headerBuilder(title, width);
    }

    public static void printHeader(String title) {
        int padding = 2;
        int width = title.length() + padding * 2;

        headerBuilder(title, width);
    }

    private static void headerBuilder(String title, int width) {
        System.out.printf("%s%s%s%n", "╔", "═".repeat(width), "╗");

        int left = (width - title.length()) / 2;
        int right = width - title.length() - left;

        System.out.printf("%s%s%s%s%s%n", "║", " ".repeat(left), title, " ".repeat(right), "║");
        System.out.printf("%s%s%s%n", "╚", "═".repeat(width), "╝");
    }

    public static void printText(String[] options){
        for (String option : options){
            System.out.println(option);
        }
    }
}
