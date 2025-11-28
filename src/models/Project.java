package models;

import models.utils.IDGenerator;
import models.utils.Status;

public abstract class Project {

    private String id;
    private String name;
    private String description;
    private int budget;
    private String type;
    private int teamSize;
    private Task[] tasks = new Task[5];
    private int taskCount = 0;

    private static IDGenerator idGenerator = new IDGenerator();
    private static int count = 0;
    private static Project[] allProjects = new Project[5];


    public static Project[] getAllProjects() {
        return allProjects;
    }


    public Project(String name, String type, String description, int budget, int teamSize) {
        this.id = idGenerator.setID('P');
        this.name = name;
        this.type = type;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        allProjects[count++] = this;
    }


    public abstract void getProjectDetails();
    public abstract void removeTask(String taskId);
    public abstract void displayTask();
    public abstract void updateTaskStatus(Status status, String taskID);

    public static void displayProjects(){
        System.out.println("ID  | NAME | BUDGET ");
        for (Project project : allProjects) {
            if (project != null){
                System.out.println(project.id + " | " + project.name + " | " + project.budget);
            }
        }
    }

    public void addTask(String name, Status status) {
        int elementsSize = 0;
        for (Task value : tasks) {
            if (value == null) {
                continue;
            }
            elementsSize++;
        }
        if (tasks.length == elementsSize){
            Task[] newTasks = new Task[elementsSize * 2];
            for (int i = 0; i < newTasks.length; i++){
                newTasks[i] = tasks[i];
                tasks = newTasks;
            }
        }
        Task task = new Task();

        // 2. Generate a formatted ID (like T001)
        task.setTaskID(idGenerator.setID('T'));

        // 3. Set task properties
        task.setName(name);
        task.setStatus(status);
        tasks[elementsSize] = task;
    }

    protected int getTaskIndex(String taskId){
        // Find the index of a task based on its ID
        for (int i = 0; i < getTasks().length; i++){
            if (getTasks()[i] != null && getTasks()[i].getTaskID().equals(taskId)){
                return i;
            }
        }
        return -1;
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
    public Task[] getTasks() {
        return tasks;
    }
}
