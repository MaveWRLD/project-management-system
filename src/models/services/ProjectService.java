package models.services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;

public class ProjectService {

    public void createSoftwareProject(String id, String name, String description, int budget, int teamSize){
        new SoftwareProject(id, name, description, budget, teamSize);
    }
    public void createHardwareProject(String id, String name, String description, int budget, int teamSize){
        new HardwareProject(id, name, description, budget, teamSize);
    }

    public void filterSoftwareProject(){
        for (Project project : Project.getAllProjects()){
            if (project instanceof SoftwareProject){
                System.out.println(project.getId() + " | " + project.getName() + " | " + project.getBudget() + " | " + project.getTeamSize())
                ;
            }
        }
    }

    public void filterHardwareProject(){
        for (Project project : Project.getAllProjects()){
            if (project instanceof HardwareProject){
                System.out.println(project.getId() + " | " + project.getName() + " | " + project.getBudget() + " | " + project.getTeamSize());
            }
        }
    }

    public void filterProjectsBudgetRange(int minBudget, int maxBudget){
        var found = false;
        for (Project project : Project.getAllProjects()){
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
