package models;

import java.io.ObjectInputFilter;
import java.text.NumberFormat;

public class SoftwareProject extends Project {

    public SoftwareProject(String id, String name, String description, int budget, int teamSize) {
        super(id, name, description, budget, teamSize);
        setType("Software Project");
    }

    @Override
    public void getProjectDetails() {
        System.out.println("Project Details: " + getId());
        System.out.println("Project Name: " + getName());
        System.out.println("Team Size: " + getTeamSize());
        System.out.println("Project Budget: " + NumberFormat.getCurrencyInstance().format(getBudget()));
    }
}
