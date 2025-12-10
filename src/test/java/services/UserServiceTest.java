package test.java.services;

import models.AdminUser;
import models.RegularUser;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.ProjectService;
import services.TaskService;
import services.UserService;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private ProjectService projectService;
    private TaskService taskService;
    private UserService userService;

    @BeforeEach
    void setup() {
        projectService = Mockito.mock(ProjectService.class);
        taskService = Mockito.mock(TaskService.class);

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
}

