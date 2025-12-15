package services;

import models.Project;
import utils.ResizeObjectSizeUtils;
import utils.exceptions.ProjectsNotCreatedException;
import utils.exceptions.ProjectNotFoundException;

import java.util.Arrays;

public class ProjectService {

    private Project[] projects = new Project[10];


    /**
     * Retrieves all existing projects.
     *
     * <p>This method returns an array of {@link Project} objects representing all the projects
     * currently available in the system. If no projects have been created, it throws a
     * {@link ProjectsNotCreatedException} to indicate that the project list is empty </p>
     *
     * @return an array of {@link Project} instances;
     * @throws ProjectsNotCreatedException if no projects have been created (i.e., the projects array has null elements).
     * @see Project
     * @see ProjectsNotCreatedException
     */
    public Project[] allProjects() throws ProjectsNotCreatedException {
        if (ResizeObjectSizeUtils.countElements(projects) == 0) {
            throw new ProjectsNotCreatedException("No Projects created");
        }
        return projects;
    }


    /**
     * Adds a new project to the collection of projects.
     *
     * <p>This method ensures that the internal projects array has enough capacity to store
     * the new {@link Project}. If the array is full, it is resized automatically using
     * {@link ResizeObjectSizeUtils#resizeObjectsSizeIfFull(Object[])}. The project is then
     * inserted at the first available (null) position in the array.</p>
     *
     * @param project the {@link Project} instance to add;
     * @see Project
     * @see ResizeObjectSizeUtils#resizeObjectsSizeIfFull(Object[])
     */
    public void addProject(Project project) {
        projects = ResizeObjectSizeUtils.resizeObjectsSizeIfFull(projects);
        int nullIndex = getFirstNullIndex(projects);
        projects[nullIndex] = project;

    }


    /**
     * Filters projects by a specified type.
     *
     * <p>This method iterates through the existing projects and returns a new array containing
     * only those {@link Project} instances whose type matches the given {@code projectType}.</p>
     *
     */
    public Project[] filterProject(String projectType) {
        Project[] filteredProjects = new Project[projects.length];
        int i = 0;
        for (Project project : projects) {
            if (project != null && project.getType().equals(projectType)) {
                filteredProjects[i] = project;
                i++;
            }
        }
        return Arrays.copyOf(filteredProjects, i);

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
    public Project[] filterProject(int minBudget, int maxBudget) {
        Project[] filteredProjects = new Project[projects.length];
        int i = 0;
        for (Project project : projects) {
            if (project != null && project.getBudget() > minBudget && project.getBudget() < maxBudget) {
                filteredProjects[i] = project;
                i++;
            }
        }
        return Arrays.copyOf(filteredProjects, i);
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
        for (Project project : projects) {
            if (project != null && project.getId().equals(projectID)) {
                return project;
            }
        }
        throw new ProjectNotFoundException(projectID);
    }

    /**
     * Finds the index of the first null element in the given projects array.
     *
     * <p>This method iterates through the provided {@link Project} array and returns the index
     * of the first position that contains {@code null}. If no {@code null} elements are found,
     * it returns {@code -1}.</p>
     *
     */
    private static int getFirstNullIndex(Project[] projects) {
        for (int i = 0; i < projects.length; i++) {
            if (projects[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
