package test.java.services;

import models.Project;
import models.SoftwareProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ProjectService;
import utils.exceptions.ProjectNotFoundException;

import static org.assertj.core.api.Assertions.*;

class ProjectServiceTest {

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();
    }

    @Test
    void testCreateProject_success() throws Exception {
        Project createdProject = new SoftwareProject("SoftwareProject1", "Test", 5000, 5, "Java", "Web", "Git");

        projectService.addProject(createdProject);

        Project result = projectService.filterProjectBYId(createdProject.getId());
        assertThat(result)
                .isNotNull()
                .extracting(Project::getName)
                .isEqualTo("SoftwareProject1");
    }

    @Test
    void testGetProjectById_notFound() {
        Throwable thrown = catchThrowable(() -> projectService.filterProjectBYId("X99"));

        assertThat(thrown)
                .isInstanceOf(ProjectNotFoundException.class)
                .hasMessageContaining("Project with ID X99 was not found");
    }

    @Test
    void testFilterByBudgetRange() throws Exception {
        projectService.addProject(new SoftwareProject("Range Project", "A", 2000, 4, "Java", "Backend", "Git"));
        projectService.addProject(new SoftwareProject("By Budget", "B", 8000, 6, "React", "Frontend", "Git"));

        Project[] filtered = projectService.filterProject(1000, 5000);

        assertThat(filtered)
                .hasSize(1)
                .extracting(Project::getName)
                .containsExactly("Range Project");
    }
}
