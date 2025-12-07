package models;

import services.ProjectService;
import services.TaskService;

public class AdminUser extends User{

    public AdminUser(String name, String email) {
        super(name, email);
    }

    TaskService taskService = new TaskService();
    ProjectService projectService = new ProjectService();

    @Override
    public void removeTask(String projectID, String taskId){
            Project project = projectService.filterProjectBYId(projectID);
            int taskIndex = taskService.getTaskIndex(project, taskId);
            for (int i = taskIndex; i < project.getTasks().length -1; i++) {
                project.getTasks()[i] = project.getTasks()[i + 1];
            }
            int lastIndex = project.getTasks().length - 1;
            project.getTasks()[lastIndex] = null;
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
