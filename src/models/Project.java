package models;

import utils.IDGenerator;

public abstract class Project {

    private String id;
    private String name;
    private String description;
    private int budget;
    private String type;
    private int teamSize;
    private Task[] tasks;


    private IDGenerator taskIdGenerator = new IDGenerator();

    public Project(String name, String type, String description, int budget, int teamSize) {
        this.id = idGenerator.setID('P');
        this.name = name;
        this.type = type;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
    }

    public static void displayProjects(){
        System.out.println("ID  | NAME | BUDGET ");
        for (Project project : allProjects) {
            if (project != null){
                System.out.println(project.id + " | " + project.name + " | " + project.budget);
            }
        }
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
        return taskIdGenerator.setID('T');
    }
}
