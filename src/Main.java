import models.*;
import services.*;
import utils.ConsoleMenu;
import utils.Status;
import utils.ValidationUtils;

public class Main {
    public static void main(String[] args) {
        ValidationUtils inputValidation = new ValidationUtils();
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService(projectService);
        ReportService statusReport = new ReportService(taskService, projectService);
        UserRepository repo = new UserRepository();

        UserService userService = new UserService(repo);

        ConsoleMenu consoleMenu = new ConsoleMenu(projectService, taskService, statusReport, userService, inputValidation);

        consoleMenu.run();
    }
}


