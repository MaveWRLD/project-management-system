package models;

import services.TaskService;


public class AdminUser extends User{

    public AdminUser(String name, String email) {
        super(name, email);
    }

    public AdminUser() {
    }

    @Override
    public void removeTask(String projectID, String taskId, TaskService taskService) {
        taskService.removeTask(projectID, taskId);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
