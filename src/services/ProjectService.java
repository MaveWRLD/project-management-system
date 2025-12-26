package services;

import models.Project;
import models.ProjectRepository;
import models.Task;
import utils.exceptions.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


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
    public Collection<Project> allProjects() {
        return projectRepository.getProjects();
    }

    public Map<Project, Collection<Task>> projectTaskMap(){
        return projectRepository.getProjectTaskMap();
    }

    /**
     * Adds a new project to the collection of projects.
     *
     * @param project the {@link Project} instance to add;
     * @see Project
     */
    public void addProject(Project project) {
        projectRepository.getProjects().add(project);
    }

    /**
     * Filters projects by a specified type.
     *
     * <p>This method iterates through the existing projects and returns a new array containing
     * only those {@link Project} instances whose type matches the given {@code projectType}.</p>
     *
     */
    public Collection<Project> filterProject(String projectType) {
       Collection<Project> filteredProjects = new ArrayList<>();
        for (Project project : projectRepository.getProjects()) {
            if (project != null && project.getType().equals(projectType)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
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
    public Collection<Project> filterProject(int minBudget, int maxBudget) {
        Collection<Project> filteredProjects = new ArrayList<>();
        for (Project project : projectRepository.getProjects()) {
            if (project != null && project.getBudget() > minBudget && project.getBudget() < maxBudget) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
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
        for (Project project : projectRepository.getProjects()) {
            if (project != null && project.getId().equals(projectID)) {
                return project;
            }
        }
        throw new ProjectNotFoundException(projectID);
    }
}
