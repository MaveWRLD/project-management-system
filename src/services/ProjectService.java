package services;

import models.Project;
import models.ProjectRepository;
import models.Task;
import utils.exceptions.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ProjectService {

    private final ProjectRepository projectRepository = new ProjectRepository();


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

    public Map<Project, ArrayList<Task>> projectTaskMap(){
        return projectRepository.getProjectTaskMap();
    }

    /**
     * Adds a new project to the collection of projects.
     *
     * @param project the {@link Project} instance to add;
     * @see Project
     */
    public void addProject(Project project) {
        Map<String, Project> projectMap = projectRepository.getProjects();
        projectMap.put(project.getId(), project);
    }

    /**
     * Filters projects by a specified type.
     *
     * <p>This method iterates through the existing projects and returns a new array containing
     * only those {@link Project} instances whose type matches the given {@code projectType}.</p>
     *
     */
    public Map<String, Project> filterProject(String projectType) {
//       Project filteredProjects = new ArrayList<>();
//        for (Project project : projectRepository.getProjects()) {
//            if (project != null && project.getType().equals(projectType)) {
//                filteredProjects.add(project);
//            }
//        }
        Predicate<Map.Entry<String, Project>> type = e -> e.getValue().getType().equals(projectType);
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
//        Map<String, Project> filteredProjects = new HashMap<>();
//        for (Project project : allProjects()) {
//            if (project != null && project.getBudget() > minBudget && project.getBudget() < maxBudget) {
//                filteredProjects.add(project);
//            }
//        }
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
