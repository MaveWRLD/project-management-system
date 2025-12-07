package models;

import java.text.NumberFormat;

public class HardwareProject extends Project {
    private String component;
    private float weight;


    public HardwareProject(String name, String description, int budget, int teamSize, String component, float weight) {
        super(name, "HARDWARE", description, budget, teamSize);
        this.component = component;
        this.weight = weight;
    }

    @Override
    public String[] getProjectDetails() {
        String budget =  NumberFormat.getCurrencyInstance().format(getBudget());
        return new String[]{
                getId(),
                getName(),
                Integer.toString(getTeamSize()),
                budget,
                Float.toString(getWeight()),
                getComponent()
        };
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

}
