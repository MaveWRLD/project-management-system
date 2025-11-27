package models;

import java.text.NumberFormat;

public class HardwareProject extends Project {
    private Task[] tasks = new Task[5];


    public HardwareProject(String id, String name, String description, int budget, int teamSize) {
        super(id, name, "Hardware", description, budget, teamSize);
    }


    public void addTask(Task task) {
        int elementsSize = 0;
        for (int i = 0; i < tasks.length; i++){
            if (tasks[i] == null){
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
        tasks[elementsSize] = task;  
    }

    @Override
    public void getProjectDetails() {
        System.out.println("Project Details: " + getId());
        System.out.println("Project Name: " + getName());
        System.out.println("Team Size: " + getTeamSize());
        System.out.println("Project Budget: " + NumberFormat.getCurrencyInstance().format(getBudget()));
    }


    public Task[] getTasks() {
        return tasks;
    }
}
