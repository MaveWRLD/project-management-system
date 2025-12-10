package test.java.services;

import models.Project;
import models.SoftwareProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ProjectService;
import services.ReportService;
import services.TaskService;
import utils.Status;

import static org.assertj.core.api.Assertions.*;

class ReportServiceTest {

    private TaskService taskService;
    private ProjectService projectService;
    private ReportService reportService;
    private Project project;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();
        taskService = new TaskService(projectService);
        reportService = new ReportService(taskService);

        project = new SoftwareProject("P1", "AI Project", 5000, 3, "Java", "ML", "Git");
        projectService.addProject(project);
        taskService.addTaskToProject(project.getId(), "Status Report", Status.COMPLETED);
    }

    @Test
    void testGenerateReport() throws Exception {
        float percentage = reportService.completionPercentage(project.getId());

        assertThat(percentage).isEqualTo(100f);
    }
}

