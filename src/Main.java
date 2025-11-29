import models.HardwareProject;
import models.SoftwareProject;
import models.utils.Status;
import models.services.TaskService;

public class Main {
    public static void main(String[] args) {
        HardwareProject hardwareProject = new HardwareProject("Hardwarepro", "Difficult", 233, 4);
        SoftwareProject softwareProject = new SoftwareProject("Softwarepro", "Difficult", 233, 4);
        TaskService taskSoftwareService = new TaskService(softwareProject);
        TaskService taskHardwareService = new TaskService(hardwareProject);


        taskSoftwareService.createTask("tesing", Status.PENDING);
        taskSoftwareService.createTask("tesing", Status.PENDING);
        taskSoftwareService.createTask("tesing", Status.PENDING);
        taskSoftwareService.createTask("tesing", Status.PENDING);
        taskSoftwareService.displayTask();
        System.out.println();
        taskSoftwareService.displayTask();

        System.out.println();

        taskHardwareService.createTask("tesing", Status.PENDING);
        taskHardwareService.createTask("tesing", Status.PENDING);
        taskHardwareService.createTask("tesing", Status.PENDING);
        taskHardwareService.createTask("tesing", Status.PENDING);
        taskHardwareService.displayTask();
        System.out.println();
        taskHardwareService.updateTaskStatus(Status.COMPLETED, "T003" );
        taskHardwareService.displayTask();
        System.out.println(taskHardwareService.calculateCompletionPercentage());
    }
}
