import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import models.Task;
import models.services.ProjectService;
import models.services.TaskService;

public class Main {
    public static void main(String[] args) {
        ProjectService projectService = new ProjectService();
        HardwareProject hardwareProject = new HardwareProject("H1", "Harwarepro", "Difficult", 233, 4);
        //SoftwareProject softwareProject = new SoftwareProject("S1", "Harwarepro", "Difficult", 233, 4);
        Task taskSoftware = new Task("T1", "work", "Started");
        Task taskHardware = new Task("H1", "work", "Started");

        hardwareProject.addTask(taskHardware);
       // softwareProject.addTask(taskSoftware);
        projectService.displayTask(hardwareProject);
        // System.out.println(Task.getProjectTasks(softwareProject)[0].getName());
    }
}
