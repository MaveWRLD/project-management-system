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
        HardwareProject hardwareProject = new HardwareProject("Hardwarepro", "Difficult", 233, 4);
        SoftwareProject softwareProject = new SoftwareProject("Softwarepro", "Difficult", 233, 4);
        Project.displayProjects();
        taskService.addTask("P001", "Testing", Status.COMPLETED);
        taskService.addTask("P001", "Testing", Status.PENDING);
        ReportService reportService = new ReportService();
        //System.out.println(reportService.completionPercentage(hardwareProject));
        ConsoleMenu.run();
        // System.out.println(projectService.filterProject("Software"));


    }
}


