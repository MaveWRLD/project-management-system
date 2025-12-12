package models;

import services.ProjectService;
import services.TaskService;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;

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
            Task[] tasks = taskService.getProjectTasks(project);
            int taskIndex = taskService.getTaskIndex(tasks, taskId);
            for (int i = taskIndex; i < tasks.length -1; i++) {
                tasks[i] = tasks[i + 1];
            }
            int lastIndex = tasks.length - 1;
            tasks[lastIndex] = null;
        } catch (ProjectNotFoundException | EmptyProjectException | TaskNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
