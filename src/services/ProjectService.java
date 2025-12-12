package services;

import models.Project;
import utils.ResizeObjectSizeUtils;
import utils.exceptions.ProjectsNotCreatedException;
import utils.exceptions.ProjectNotFoundException;

import java.util.Arrays;

public class ProjectService {

    private Project[] projects = new Project[10];

    public Project[] allProjects() throws ProjectsNotCreatedException {
        if (ResizeObjectSizeUtils.countElements(projects) == 0){
            throw new ProjectsNotCreatedException("No Projects created");
        }
        return projects;
    }

    public void addProject(Project project) {
        projects = ResizeObjectSizeUtils.resizeObjectsSizeIfFull(projects);
        int nullIndex = getFirstNullIndex(projects);
        projects[nullIndex] = project;
    }

    public Project[] filterProject(String projectType){
        Project[] filteredProjects = new Project[projects.length];
        int i = 0;
        for (Project project : projects){
            if (project != null && project.getType().equals(projectType)){
               filteredProjects[i] = project;
               i++;
            }
        }
        return Arrays.copyOf(filteredProjects, i);
    }

    public Project[] filterProject(int minBudget, int maxBudget){
        Project[] filteredProjects = new Project[projects.length];
        int i = 0;
            for (Project project : projects){
                if (project != null && project.getBudget() > minBudget && project.getBudget() < maxBudget){
                    filteredProjects[i] = project;
                    i++;
                }
            }
        return Arrays.copyOf(filteredProjects, i);
    }

    public Project filterProjectBYId(String projectID) throws ProjectNotFoundException {
        for (Project project : projects){
            if (project != null && project.getId().equals(projectID)){
                System.out.println(project.getId());
                System.out.println(projectID.equals(project.getId()));

                return project;
            }
        }
        throw new ProjectNotFoundException(projectID);
    }

    private static int getFirstNullIndex(Project[] projects) {
        for (int i = 0; i < projects.length; i++){
            if (projects[i] == null){
                return i;
            }
        }
        return -1;
    }
}
