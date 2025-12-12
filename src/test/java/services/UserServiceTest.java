package test.java.services;

import models.AdminUser;
import models.RegularUser;
import models.SoftwareProject;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    @BeforeEach
    void setup() {
        projectService = new ProjectService();
        taskService = new TaskService(projectService);

        userService = new UserService(projectService, taskService);
    }

    @Test
    void testAdminUserInitialization() {
        User admin = userService.getAdminUser();

        assertThat(admin)
                .isNotNull()
                .isInstanceOf(AdminUser.class);

        assertThat(admin.getName()).isEqualTo("Jacob Quaye");
        assertThat(admin.getEmail()).isEqualTo("kofimave@gmail.com");
    }

    @Test
    void testSwitchUser_fromAdminToRegular() {
        User admin = userService.getAdminUser();

        User nextUser = userService.switchUser(admin);

        assertThat(nextUser)
                .isNotNull()
                .isInstanceOf(RegularUser.class);

        assertThat(nextUser.getName()).isEqualTo("John Doe");
    }

    @Test
    void testSwitchUser_fromRegularToAdmin() {
        User regular = new RegularUser("John Doe", "johndoe@gmail.com");

        User nextUser = userService.switchUser(regular);

        assertThat(nextUser)
                .isNotNull()
                .isInstanceOf(AdminUser.class);

        assertThat(nextUser.getName()).isEqualTo("Jacob Quaye");
    }

    @Test
    void testAdminRemoveTask() {
        User admin = userService.getAdminUser();
        SoftwareProject javaProject = new SoftwareProject("Data Science", "Difficult", 233, 4,  "Python", "Mobile", "Git");
        projectService.addProject(javaProject);
        taskService.addTaskToProject("P001", "Test Admin Remove task", Status.COMPLETED);

        assertThatCode(() -> admin.removeTask("P001", "T001")).doesNotThrowAnyException();
    }
}

