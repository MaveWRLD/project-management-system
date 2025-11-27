import models.SoftwareProject;
import models.Task;
import models.services.TaskService;

public class Main {
    static void main(String[] args) {
        TaskService taskService = new TaskService();
        SoftwareProject softwareProject = new SoftwareProject("S002", "Design", "Nice Interface", 2000, 3);
        taskService.addTask("P11","Design","Pending", softwareProject );
        // System.out.println(Task.getProjectTasks(softwareProject)[0].getName());
    }
}
