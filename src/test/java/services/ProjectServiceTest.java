package test.java.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Project;
import models.ProjectRepository;
import models.SoftwareProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ProjectService;
import utils.exceptions.ProjectNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;

/**
 * The type Project service test.
 */
class ProjectServiceTest {

    private ProjectService projectService;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        projectService = new ProjectService(new ProjectRepository());
    }

    /**
     * Test create project success.
     *
     * @throws Exception the exception
     */
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

    /**
     * Test get project by id not found.
     */
    @Test
    void testGetProjectById_notFound() {
        Throwable thrown = catchThrowable(() -> projectService.filterProjectBYId("X99"));

        assertThat(thrown)
                .isInstanceOf(ProjectNotFoundException.class)
                .hasMessageContaining("Project with ID X99 was not found");
    }

    /**
     * Test filter by budget range.
     *
     * @throws Exception the exception
     */
    @Test
    void testFilterByBudgetRange() throws Exception {
        projectService.addProject(new SoftwareProject("Range Project", "A", 2000, 4, "Java", "Backend", "Git"));
        projectService.addProject(new SoftwareProject("By Budget", "B", 8000, 6, "React", "Frontend", "Git"));

        Map<String, Project> filtered = projectService.filterProject(1000, 5000);

        assertThat(filtered.values())
                .hasSize(1)
                .extracting(Project::getName)
                .containsExactly("Range Project");
    }


    @Test
    void testReadJsonUsingStreams() throws IOException {
        Path tempFile = Files.createTempFile("test", ".json");
        String jsonContent = "{\"projects\":{}}";
        Files.writeString(tempFile, jsonContent);

        String json = Files.lines(tempFile).collect(Collectors.joining());
        assertThat(json).contains("\"projects\"");
    }


    @Test
    void testSaveProjectDataToFile() throws IOException {
        ProjectRepository repo = new ProjectRepository();
        Project createdProject = new SoftwareProject("SoftwareProject1", "Test", 5000, 5, "Java", "Web", "Git");

        repo.setProjects(new HashMap<>(Map.of(createdProject.getId(), createdProject)));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(repo);

        Path tempFile = Files.createTempFile("projects", ".json");
        Files.write(tempFile, json.lines().toList());

        assertThat(tempFile).exists();
    }


    @Test
    void testConcurrentFileWrites() throws InterruptedException, IOException {
        Path tempFile = Files.createTempFile("projects", ".json");
        Runnable saveTask = () -> {
            try {
                Files.writeString(tempFile, "{\"projects\":{}}");
            } catch (IOException e) {
                fail("IOException in thread");
            }
        };

        Thread t1 = new Thread(saveTask);
        Thread t2 = new Thread(saveTask);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertThat(Files.size(tempFile)).isGreaterThan(0L);    }
}
