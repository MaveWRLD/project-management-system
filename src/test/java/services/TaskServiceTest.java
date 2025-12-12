package test.java.services;

import models.Task;
import models.Project;
import models.SoftwareProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ProjectService;
import services.TaskService;
import utils.Status;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.TaskNotFoundException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

class TaskServiceTest {

    private TaskService taskService;
    private ProjectService projectService;
    private Project project;

    @BeforeEach
    void setUp() {
        project = new SoftwareProject(
                "Java Testing",
                "Readable Codes",
                10000,
                5,
                "Java",
                "Clean Coding",
                "Git"
        );
        projectService = new ProjectService();
        projectService.addProject(project);
        taskService = new TaskService(projectService);
    }

    @Test
    void testAddTask_success() throws EmptyProjectException {

        taskService.addTaskToProject(project.getId(),"Setup Repo", Status.PENDING);
        Task[] tasks = taskService.getProjectTasks(project);

        assertThat(tasks)
                .filteredOn(Objects::nonNull)
                .extracting(Task::getName)
                .contains("Setup Repo");
    }

    @Test
    void testUpdateTaskStatus_success() throws Exception {
        taskService.addTaskToProject(project.getId(), "Design", Status.PENDING);

        taskService.updateTaskStatus(project.getId(), Status.COMPLETED, "T001");

        assertThat(project.getTasks())
                .filteredOn(Objects::nonNull)
                .extracting(Task::getStatus)
                .containsExactly(Status.COMPLETED);
    }

    @Test
    void testUpdateTaskStatus_emptyProject() {
        Throwable thrown = catchThrowable(() ->
                taskService.updateTaskStatus(project.getId(), Status.PENDING, "X99")
        );

        assertThat(thrown)
                .isInstanceOf(EmptyProjectException.class)
                .hasMessageContaining(project.getId());
    }

    @Test
    void testUpdateTaskStatus_taskNotFound() {
        taskService.addTaskToProject(project.getId(), "JUnit", Status.PENDING);

        Throwable thrown = catchThrowable(() ->
                taskService.updateTaskStatus(project.getId(), Status.PENDING, "X99")
        );

        assertThat(thrown).isInstanceOf(TaskNotFoundException.class);
    }
}

