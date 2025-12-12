package models;

import utils.IdGenareator;

public abstract class Project {

    private String id;
    private String name;
    private String description;
    private int budget;
    private String type;
    private int teamSize;
    private Task[] tasks = new Task[10];

    private final IdGenareator idGenareator = new IdGenareator();

    private static int projectCounter = 1;

    private int taskCounter = 1;


    public Project(String name, String type, String description, int budget, int teamSize) {

        this.id = idGenareator.generateProjectId('P', projectCounter++);
        this.name = name;
        this.type = type;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
    }

    @Override
    public String toString(){
        return getName();
    }

    public abstract String[] getProjectDetails();


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBudget() {
        return budget;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public String getType() {
        return type;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public Task[] getTasks() {
            return tasks;
    }

    public String generateTaskId() {
        return idGenareator.generateProjectId('T', taskCounter++);
    }
}
