package test.java.services;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ProjectService;
import services.TaskService;
import services.UserService;
import utils.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


class UserServiceTest {

    private ProjectService projectService;
    private TaskService taskService;
    private UserService userService;
    private UserRepository repo;

    /**
     * Sets up test.
     */
    @BeforeEach
    void setup() {
        projectService = new ProjectService(new ProjectRepository());
        taskService = new TaskService(projectService);
        repo = new UserRepository();

        userService = new UserService(repo);
    }

    /**
     * Test switch user from admin to regular user.
     */
    @Test
    void testSwitchUser_toRegular() throws Exception {

        User currentUser = userService.switchUser("Appiah");

        assertThat(currentUser)
                .isNotNull()
                .isInstanceOf(AdminUser.class);

        assertThat(currentUser.getName()).isEqualToIgnoringCase("Appiah");
    }

    /**
     * Test switch user to admin user.
     */
    @Test
    void testSwitchUser_adminUser() throws Exception {
        User currentUser = userService.switchUser("kwame");

        assertThat(currentUser)
                .isNotNull()
                .isInstanceOf(RegularUser.class);

        assertThat(currentUser.getName()).isEqualToIgnoringCase("kwame");
    }

    /**
     * Test admin remove task.
     */
    @Test
    void testAdminRemoveTask() throws Exception {
        User admin = userService.switchUser("Appiah");
        SoftwareProject javaProject = new SoftwareProject("Data Science", "Difficult", 233, 4,  "Python", "Mobile", "Git");
        projectService.addProject(javaProject);
        taskService.addTaskToProject("P001", "Test Admin Remove task", Status.COMPLETED);

        assertThatCode(() -> admin.removeTask("P001", "T001", taskService)).doesNotThrowAnyException();
    }
}

