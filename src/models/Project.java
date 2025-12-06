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
    private static IDGenerator idGenerator = new IDGenerator();
    private static int count = 0;
    private static Project[] allProjects = new Project[5];


    private IDGenerator taskIdGenerator = new IDGenerator();

    public Project(String name, String type, String description, int budget, int teamSize) {
        this.id = idGenerator.setID('P');
        this.name = name;
        this.type = type;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;

        if (count == allProjects.length) {
            Project[] newArray = new Project[allProjects.length * 2];
            System.arraycopy(allProjects, 0, newArray, 0, allProjects.length);
            allProjects = newArray;
        }
        allProjects[count++] = this;
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

    public static Project[] getAllProjects() {
        return allProjects;
    }

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
        if (tasks != null)
            return tasks;
        return new Task[5];
    }

    public String generateTaskId() {
        return taskIdGenerator.setID('T');
    }
}
