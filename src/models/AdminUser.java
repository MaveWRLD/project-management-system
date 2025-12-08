package models;

import services.ProjectService;
import services.TaskService;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;

public class AdminUser extends User{

    public AdminUser(String name, String email) {
        super(name, email);
    }

    TaskService taskService = new TaskService();
    ProjectService projectService = new ProjectService();

    @Override
    public void removeTask(String projectID, String taskId) throws TaskNotFoundException {
        try {
            Project project = projectService.filterProjectBYId(projectID);
            int taskIndex = taskService.getTaskIndex(project, taskId);
            for (int i = taskIndex; i < project.getTasks().length -1; i++) {
                project.getTasks()[i] = project.getTasks()[i + 1];
            }
            int lastIndex = project.getTasks().length - 1;
            project.getTasks()[lastIndex] = null;
        } catch (ProjectNotFoundException | EmptyProjectException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
