import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import models.utils.Status;
import models.Task;
import models.services.ProjectService;
import models.services.TaskService;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        HardwareProject hardwareProject = new HardwareProject("Hardwarepro", "Difficult", 233, 4);
        SoftwareProject softwareProject = new SoftwareProject("Softwarepro", "Difficult", 233, 4);
        softwareProject.addTask("Java Basics", Status.COMPLETED);
        softwareProject.addTask("Java Basics", Status.COMPLETED);
        softwareProject.addTask("Java Basics", Status.PENDING);

        //softwareProject.addTask("Java Basics", Status.PENDING);


        taskService.isCompleted(softwareProject);
        float percentage = taskService.calculateCompletionPercentage(softwareProject);
        System.out.println(percentage);
        Project.displayProjects();




        // System.out.println(Task.getProjectTasks(softwareProject)[0].getName());
    }
}
