package models;

import models.utils.Status;

import java.text.NumberFormat;

public class SoftwareProject extends Project {

    public SoftwareProject(String name, String description, int budget, int teamSize) {
        super(name, "Software", description, budget, teamSize);
    }
    

    @Override
    public void getProjectDetails() {
        System.out.println("Project Details: " + getId());
        System.out.println("Project Name: " + getName());
        System.out.println("Team Size: " + getTeamSize());
        System.out.println("Project Budget: " + NumberFormat.getCurrencyInstance().format(getBudget()));
    }
}
