package models;

public abstract class Project {
    private String id;
    private String name;
    private String type;
    private String description;
    private int budget;
    private int teamSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }



    public Project(String id, String name){
        this.id = setId(id);
    }

    public abstract void getDetails();

    public void displayProject(){
        System.out.println("id: " + id + " name: " + name + " description: " + description + " budget: " + budget + " teamSize: " + teamSize);
    }
}
