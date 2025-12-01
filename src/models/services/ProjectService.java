package models.services;

import models.Project;
import models.utils.Status;

public class ProjectService {
    private final Project[] projects = Project.getAllProjects();

    public Project[] filterProject(String projectType){
        Project[] filteredProjects = new Project[projects.length];
        int i = 0;
        for (Project project : projects){
            if (project == null){
                continue;
            }
            if (project.getType().equals(projectType)){
               filteredProjects[i] = project;
               i++;
            }
        }
        return filteredProjects;
    }

    
    public Project[] filterProject(int minBudget, int maxBudget){
        // Filter Project based on budget range
        Project[] filteredProjects = new Project[projects.length];
        int i = 0;
        for (Project project : projects){
            if (project == null){
                continue;
            }
            if (project.getBudget() > minBudget && project.getBudget() < maxBudget){
                filteredProjects[i] = project;
                i++;
            }
        }
        return filteredProjects;
    }

    public Project filterProjectBYId(String projectID){
        Project filteredProjects = null;
        for (Project project : projects){
            if (project == null){
                continue;
            }
            if (project.getId().equals(projectID)){
                filteredProjects = project;
                break;
            }
        }
        return filteredProjects;
    }
}
