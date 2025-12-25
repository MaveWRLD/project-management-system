package models;

import java.text.NumberFormat;

public class SoftwareProject extends Project {
    private final String technology;
    private final String domain;
    private final String versioning;

    public SoftwareProject(String name, String description, int budget, int teamSize, String technology, String domain, String versioning) {
        super(name, "SOFTWARE", description, budget, teamSize);
        this.technology = technology;
        this.domain = domain;
        this.versioning = versioning;
    }

    @Override
    public String[] getProjectDetails() {
        String budget =  NumberFormat.getCurrencyInstance().format(getBudget());
        return new String[] {
                getId(),
                getName(),
                Integer.toString(getTeamSize()),
                budget,
                technology,
                domain,
                versioning
        };
    }
}
