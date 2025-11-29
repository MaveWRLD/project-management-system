package models;

import models.utils.Status;

import java.text.NumberFormat;

public class HardwareProject extends Project {
    public HardwareProject(String name, String description, int budget, int teamSize) {
        super(name, "Hardware", description, budget, teamSize);
    }

    @Override
    public void getProjectDetails() {
        System.out.println("Project Details: " + getId());
        System.out.println("Project Name: " + getName());
        System.out.println("Team Size: " + getTeamSize());
        System.out.println("Project Budget: " + NumberFormat.getCurrencyInstance().format(getBudget()));
    }
}
