package services;

import models.Project;

public class ProjectService {

    private Project[] projects = Project.getAllProjects();

    public void addProject(Project project) {
        projects = resizeProjectsSizeIfFull(projects);
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
        return filteredProjects;
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
        return filteredProjects;
    }

    public Project filterProjectBYId(String projectID){
        Project filteredProjects = null;
        for (Project project : projects){
            if (project != null && project.getId().equals(projectID)){
                filteredProjects = project;
                break;
            }
        }
        return filteredProjects;
    }

    private static int getFirstNullIndex(Project[] projects) {
        for (int i = 0; i < projects.length; i++){
            if (projects[i] == null){
                return i;
            }
        }
        return -1;
    }

    private static Project[] resizeProjectsSizeIfFull(Project[] projects) {
        int elementsSize = getElementsSize(projects);
        if (projects.length == elementsSize) {
            Project[] newProjects = new Project[elementsSize * 2];
            System.arraycopy(projects, 0, newProjects, 0, projects.length);
            return newProjects;
        }
        return projects;
    }

    private static int getElementsSize(Project[] projects) {
        int elementsSize = 0;
        for (Project oldProject : projects) {
            if (oldProject != null) {
                elementsSize++;
            }
        }
        return elementsSize;
    }
}
