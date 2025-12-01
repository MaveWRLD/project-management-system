import models.*;
import models.utils.ConsoleMenu;
import models.utils.Status;
import models.services.ProjectService;
import models.services.ReportService;
import models.services.TaskService;

public class Main {
    public static void main(String[] args) {
        ReportService statusReport = new ReportService();
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService();
        HardwareProject systemUnitProject = new HardwareProject("System unit Repair", "Difficult", 233, 4);
        HardwareProject monitorProject = new HardwareProject("Monitor", "Difficult", 233, 4);
        HardwareProject keyboardProject = new HardwareProject("Keyboard", "Difficult", 233, 4);

        SoftwareProject javaProject = new SoftwareProject("Java", "Difficult", 233, 4);
        SoftwareProject PythonProject = new SoftwareProject("Softwarepro", "Difficult", 233, 4);
        Project.displayProjects();
        taskService.addTask("P001", "Testing", Status.COMPLETED);
        taskService.addTask("P004", "Programming", Status.PENDING);
        ReportService reportService = new ReportService();
        ConsoleMenu.run();
    }
}


