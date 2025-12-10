package services;

import models.AdminUser;
import models.RegularUser;
import models.User;

public class UserService {
    private ProjectService projectService;
    private TaskService taskService;

    private User adminUser;
    private User regularUser;

    public UserService(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;

        this.adminUser = new AdminUser("Jacob Quaye", "kofimave@gmail.com", projectService, taskService);
        this.regularUser = new RegularUser("John Doe", "johndoe@gmail.com");
    }

    public User switchUser(User currentUser){
        if (currentUser instanceof AdminUser)
            return regularUser;
        return adminUser;
    }

    public User getAdminUser() {
        return adminUser;
    }
}
