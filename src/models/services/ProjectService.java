package models.services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import models.Task;

public class ProjectService {

    

    // public void createProject(String id, String name, String description, int budget, int teamSize){
    //     return new HardwareProject(id, name, description, budget, teamSize);
    // }

    private Project[] projects = Project.getAllProjects();

    public void displayTask(HardwareProject hardwareProject){
        System.out.println("ID  | NAME | Status ");
        for (Task task : hardwareProject.getTasks()) {
            if (task != null){
                System.out.println(task.getId() + " | " + task.getName() + " | " + task.getStatus());
            }
        }
    }


    public void filterProject(String projectType){
        // Filter Project based on project type
        for (Project project : projects){
            if (project.getType().equals(projectType)){
                System.out.println(project.getId() + " | " + project.getName() + " | " + project.getBudget() + " | " + project.getTeamSize())
                ;
            }
        }
    }

    public void filterProject(int minBudget, int maxBudget){
        // Filter Project based on budget range
        var found = false;
        for (Project project : projects){
            if (project == null){
                continue;
            }
            if (project.getBudget() > minBudget && project.getBudget() < maxBudget){
                System.out.println(project.getId() + " | " + project.getName() + " | " + project.getBudget() + " | " + project.getTeamSize());
                found = true;
            }
        }
        if (!found){
            System.out.println("No projects found");
        }
    }
}
