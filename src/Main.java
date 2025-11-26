import models.Project;
import models.Task;
import models.services.ProjectService;
import models.services.TaskService;

public class Main {
    static void main(String[] args) {
        TaskService.addTask("Design", "P11", "Pending" );
        System.out.println(Task.getAllTask()[0].getStatus());
        TaskService.updateTaskStatus("P11", "C");
        System.out.println(Task.getAllTask()[0].getStatus());

    }
}
