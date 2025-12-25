package models;

import services.ProjectService;
import services.TaskService;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;

import java.util.Collection;


public class AdminUser extends User{

    private ProjectService projectService;
    private TaskService taskService;

    public AdminUser(String name, String email, ProjectService projectService, TaskService taskService) {
        super(name, email);
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public void removeTask(String projectID, String taskId) {
        try {
            Project project = projectService.filterProjectBYId(projectID);
            Collection<Task> tasks = taskService.getProjectTasks(project);
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
