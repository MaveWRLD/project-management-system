import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import models.StatusReport;
import models.utils.Status;
import models.services.ProjectService;
import models.services.ReportService;
import models.services.TaskService;

public class Main {
    public static void main(String[] args) {
        ReportService statusReport = new ReportService();

        Project[] projects = Project.getAllProjects();
        HardwareProject hardwareProject = new HardwareProject("Hardwarepro", "Difficult", 233, 4);
        SoftwareProject softwareProject = new SoftwareProject("Softwarepro", "Difficult", 233, 4);
        TaskService taskSoftwareService = new TaskService(softwareProject);
        TaskService taskHardwareService = new TaskService(hardwareProject);


        taskSoftwareService.createTask("tesing", Status.COMPLETED);
        taskSoftwareService.createTask("tesing", Status.PENDING);
        taskSoftwareService.createTask("tesing", Status.PENDING);
        taskSoftwareService.createTask("tesing", Status.PENDING);
        //taskSoftwareService.displayTask();
        System.out.println();
        //taskSoftwareService.displayTask();

        System.out.println();

        taskHardwareService.createTask("tesing", Status.COMPLETED);
        taskHardwareService.createTask("tesing", Status.COMPLETED);
        taskHardwareService.createTask("tesing", Status.PENDING);
        taskHardwareService.createTask("tesing", Status.PENDING);
        System.out.println(statusReport.completionPercentage(softwareProject));
        //taskHardwareService.displayTask();
        System.out.println();
        
        StatusReport[] reports = statusReport.generateReport(projects);
        

        for (StatusReport report: reports){
            if (report == null){
                continue;
            }
           System.out.println(report.getProjectID() + " " + report.getProjectName() + " " + report.getTotalTask() + " " + report.getCompletedTasks() + " " + report.getCompletionPecentage());
        }
    }
}
