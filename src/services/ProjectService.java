package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Project;
import models.ProjectRepository;
import utils.exceptions.ProjectNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void loadProjectsRepository() {
        Path filePath = Path.of("projects_data.json");
        try {
            if (Files.exists(filePath) && Files.size(filePath) > 0) {
                ObjectMapper objectMapper = new ObjectMapper();

                String json = Files.lines(filePath)
                        .collect(Collectors.joining());

                projectRepository = objectMapper.readValue(json, ProjectRepository.class);
            }
        } catch (IOException e) {
            System.out.println("Failed to load project repository: " + e.getMessage());
            projectRepository = new ProjectRepository();
        }
    }

    public void saveProjectDataToFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ProjectRepository repo = new ProjectRepository(projectRepository.getProjects());

            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repo);

            Path filePath = Path.of("projects_data.json");

            Files.write(filePath, json.lines().toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all existing projects.
     *
     * <p>This method returns an array of {@link Project} objects representing all the projects
     * currently available in the system.
     *
     * @return an array of {@link Project} instances;
     * @see Project
     */
    public Map<String, Project> allProjects() {
        return projectRepository.getProjects();
    }

    /**
     * Adds a new project to the collection of projects.
     *
     * @param project the {@link Project} instance to add;
     * @see Project
     */
    public void addProject(Project project) {
        projectRepository.getProjects().put(project.getId(), project);
    }

    /**
     * Filters projects by a specified type.
     *
     * <p>This method iterates through the existing projects and returns a new array containing
     * only those {@link Project} instances whose type matches the given {@code projectType}.</p>
     *
     */
    public Map<String, Project> filterProject(String projectType) {
        Predicate<Map.Entry<String, Project>> type = e -> e.getValue().getProjectType().equals(projectType);
        return allProjects().entrySet().stream().filter(type).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }


    /**
     * Filters projects based on a specified budget range.
     *
     * <p>This method returns a new array containing all {@link Project} instances whose budget
     * is strictly greater than minBudget and strictly less than maxBudget.</p>
     *
     * @return an array of {@link Project} objects that fall within the specified budget range.
     * If no projects match, an empty array is returned (never {@code null}).
     *
     */
    public Map<String, Project> filterProject(int minBudget, int maxBudget) {

        Predicate<Map.Entry<String, Project>> minRange = e -> e.getValue().getBudget() > minBudget;
        Predicate<Map.Entry<String, Project>> maxRange = e -> e.getValue().getBudget() < maxBudget;

        return allProjects().entrySet().stream().filter(minRange.and(maxRange)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Retrieves a project by its ID.
     *
     * <p>This method searches through the existing projects and returns the {@link Project}
     * instance whose ID matches the specified projectID. If no matching project is found,
     * a {@link ProjectNotFoundException} is thrown.</p>
     *
     * @see Project
     * @see ProjectNotFoundException
     */
    public Project filterProjectBYId(String projectID) throws ProjectNotFoundException {
        if (allProjects().containsKey(projectID))
            return allProjects().get(projectID);
        throw new ProjectNotFoundException(projectID);
    }
}
