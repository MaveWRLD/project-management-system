import models.*;
import utils.ConsoleMenu;
import utils.Status;
import services.ProjectService;
import services.ReportService;
import services.TaskService;

public class Main {
    public static void main(String[] args) {
        ReportService statusReport = new ReportService();
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService();
        HardwareProject systemUnitProject = new HardwareProject("System unit Repair", "Difficult", 233, 4, "Screen", 1.4f);
        HardwareProject monitorProject = new HardwareProject("Monitor", "Difficult", 233, 4, "Board", 1.6f);
        HardwareProject keyboardProject = new HardwareProject("Keyboard", "Difficult", 233, 4, "Chip", 0.4f);

        SoftwareProject javaProject = new SoftwareProject("Data Science", "Difficult", 233, 4,  "Python", "Mobile", "Git");
        SoftwareProject PythonProject = new SoftwareProject("Java Basics", "Difficult", 233, 4, "Java", "Web", "Git");


        taskService.addTaskToProject("P001", "Testing", Status.COMPLETED);
        taskService.addTaskToProject("P004", "Programming", Status.PENDING);
        ReportService reportService = new ReportService();
        ConsoleMenu.run();
    }
}


