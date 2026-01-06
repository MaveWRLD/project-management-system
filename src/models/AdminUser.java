package models;

import services.ProjectService;
import services.TaskService;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;

import java.util.Collection;


public class AdminUser extends User{

    private final ProjectService projectService = new ProjectService();
    private final TaskService taskService = new TaskService(projectService);

    public AdminUser(String name, String email) {
        super(name, email);

    }

    public AdminUser() {
    }

    @Override
    public void removeTask(String projectID, String taskId) {
        try {
            Collection<Task> tasks = taskService.getProjectTasks(projectID);
            Task task = taskService.getTask(tasks, taskId);
            tasks.remove(task);
        } catch (ProjectNotFoundException | TaskNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
