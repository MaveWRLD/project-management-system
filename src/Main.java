import models.*;
import services.*;
import utils.ConsoleMenu;
import utils.Status;
import utils.ValidationUtils;
import utils.exceptions.UserNotFoundException;

public class Main {
    public static void main(String[] args) {
        ValidationUtils inputValidation = new ValidationUtils();
        ProjectRepository projectRepository = new ProjectRepository();
        ProjectService projectService = new ProjectService(projectRepository);
        TaskService taskService = new TaskService(projectService);
        ReportService statusReport = new ReportService(taskService, projectService);
        UserRepository repo = new UserRepository();

        HardwareProject projectP001 = new HardwareProject(
                "System unit Repair","Difficult",233,4,"Screen",1.4f
        );

        HardwareProject projectP003 = new HardwareProject(
                "Keyboard","Difficult",233,4,"Chip",0.4f
        );

        HardwareProject projectP002 = new HardwareProject(
                "Monitor","Difficult",233,4,"Board",1.6f
        );

        SoftwareProject projectP004 = new SoftwareProject(
                "Data Science","Difficult",233,4,"Python", "Data Analytics","Git"                    // versioning
        );

        SoftwareProject projectP005 = new SoftwareProject(
                "Java Basics","Difficult",233,4,"Java","Programming Fundamentals","Git"                    // versioning
        );

        projectService.addProject(projectP001);
        projectService.addProject(projectP002);
        projectService.addProject(projectP003);
        projectService.addProject(projectP004);
        projectService.addProject(projectP005);

        taskService.addTaskToProject("P001", "Optimization", Status.COMPLETED);
        taskService.addTaskToProject("P001", "Optimal", Status.COMPLETED);

        taskService.addTaskToProject("P004", "Debugging", Status.IN_PROGRESS);

        UserService userService = new UserService(repo);

        ConsoleMenu consoleMenu = new ConsoleMenu(projectService, taskService, statusReport, userService, inputValidation);

        System.out.println("Login Page");
        LoginPageService loginPageService = new LoginPageService(userService);
        User user;
        try {
            user = loginPageService.login();
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        consoleMenu.run(user);
    }
}
