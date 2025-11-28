package models;

import models.utils.Status;

import java.text.NumberFormat;

public class HardwareProject extends Project {
    public HardwareProject(String name, String description, int budget, int teamSize) {
        super(name, "Hardware", description, budget, teamSize);
    }

    @Override
    public void displayTask(){
        System.out.println("ID  | NAME | Status ");
        for (Task task : getTasks()) {
            if (task != null){
                System.out.println(task.getTaskID() + " | " + task.getName() + " | " + task.getStatus());
            }
        }

    }

    @Override
    public void updateTaskStatus(Status status, String taskID){
        for (int i = 0; i < getTasks().length; i++){
            if (getTasks()[i] == null){
                continue;
            }
            if (getTasks()[i].getTaskID().equals(taskID)){
                getTasks()[i].setStatus(status);
            }
        }
    }


    @Override
    public void getProjectDetails() {
        System.out.println("Project Details: " + getId());
        System.out.println("Project Name: " + getName());
        System.out.println("Team Size: " + getTeamSize());
        System.out.println("Project Budget: " + NumberFormat.getCurrencyInstance().format(getBudget()));
    }

    @Override
    public void removeTask(String taskId) {
        int taskIndex = getTaskIndex(taskId);
        for (int i = taskIndex; i < getTasks().length - 1; i++) {
            getTasks()[i] = getTasks()[i + 1];
        }
        getTasks()[getTasks().length - 1] = null;
    }
}
