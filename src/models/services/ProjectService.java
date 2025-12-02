package models.services;

import models.Project;
import models.Task;
import models.utils.Status;

public class ProjectService {

    private Project[] projects = Project.getAllProjects();

    public void addProject(Project project) {
        projects = assignProjectSizeIfFull(projects);
        for (int i = 0; i < projects.length; i++){
            if (projects[i] == null){
                projects[i] = project;
                break;
            }
        }
    }

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

    private static Project[] assignProjectSizeIfFull(Project[] projects) {
        int elementsSize = 0;
        for (Project oldProject : projects) {
            if (oldProject != null) {
                elementsSize++;
            }
        }

        if (projects.length == elementsSize) {
            Project[] newProjects = new Project[elementsSize * 2];
            System.arraycopy(projects, 0, newProjects, 0, projects.length);
            return newProjects;
        }

        return projects;
    }


    public Project[] getProjects() {
        return projects;
    }

    public void setProjects(Project[] projects) {
        this.projects = projects;
    }
}
