import models.*;
import services.UserService;
import utils.ConsoleMenu;
import utils.Status;
import services.ProjectService;
import services.ReportService;
import services.TaskService;
import utils.ValidationUtils;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.ProjectsNotCreatedException;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ValidationUtils inputValidation = new ValidationUtils();
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService(projectService);
        ReportService statusReport = new ReportService(taskService, projectService);
        HardwareProject systemUnitProject = new HardwareProject("System unit Repair", "Difficult", 233, 4, "Screen", 1.4f);
        HardwareProject monitorProject = new HardwareProject("Monitor", "Difficult", 233, 4, "Board", 1.6f);
        HardwareProject keyboardProject = new HardwareProject("Keyboard", "Difficult", 233, 4, "Chip", 0.4f);
        SoftwareProject javaProject = new SoftwareProject("Data Science", "Difficult", 233, 4,  "Python", "Mobile", "Git");
        SoftwareProject pythonProject = new SoftwareProject("Java Basics", "Difficult", 233, 4, "Java", "Web", "Git");

        projectService.addProject(systemUnitProject);
        projectService.addProject(monitorProject);
        projectService.addProject(keyboardProject);
        projectService.addProject(javaProject);
        projectService.addProject(pythonProject);


        taskService.addTaskToProject("P001", "Testing", Status.COMPLETED);
        taskService.addTaskToProject("P004", "Programming", Status.PENDING);

        UserService userService = new UserService(projectService, taskService);

        User currentUser = userService.getAdminUser();

        ConsoleMenu consoleMenu = new ConsoleMenu(projectService, taskService, statusReport, userService, currentUser, inputValidation);

        consoleMenu.run();
    }
}


