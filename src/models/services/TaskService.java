package models.services;

import models.*;
import models.utils.IDGenerator;
import models.utils.Status;


public class TaskService {
    private Project project;

    private IDGenerator idGenerator= new IDGenerator();
    

    public TaskService(Project project) {
        this.project = project;
    }

    public void createTask(String name, Status status){
        Task task = new Task();
        task.setTaskID(idGenerator.setID('T'));
        task.setName(name);
        task.setStatus(status);
        addTask(task);
    }

    public void addTask(Task newTask) {
        if (project.getTasks() == null){
            project.setTasks(new Task[5]);
        }
        int elementsSize = 0;
        for (Task oldTask : project.getTasks()) {
            if (oldTask == null) {
                continue;
            }
            elementsSize++;
        }
        if (project.getTasks().length == elementsSize){
            Task[] newTasks = new Task[elementsSize * 2];
            for (int i = 0; i < newTasks.length; i++){
                newTasks[i] = project.getTasks()[i];
            }
            newTasks[elementsSize] = newTask;
            project.setTasks(newTasks);
        }
        project.getTasks()[elementsSize] = newTask;
    }

    public boolean isCompleted(){
        return project.getTasks()[0].isCompleted();
    }


    public float calculateCompletionPercentage(){
        float completed = 0;
        float totalTasks = 0;
        for (Task task : project.getTasks()){
            if (task == null){
                continue;
            }
            ++totalTasks; 

            if (task.isCompleted()){
                ++completed;
            }
        }
        System.out.println("totalTask: " + totalTasks); 
        System.out.println("completed: " + completed); 
        System.out.println("pecentage: " + (completed / totalTasks) * 100); 
        return (completed / totalTasks) * 100;
    }
    
    public void displayTask(){
        System.out.println("ID  | NAME | Status ");
        for (Task task : project.getTasks()) {
            if (task != null){
                System.out.println(task.getTaskID() + " | " + task.getName() + " | " + task.getStatus());
            }
        }
    }

    public void updateTaskStatus(Status status, String taskID){
        for (int i = 0; i < project.getTasks().length; i++){
            if (project.getTasks()[i] == null){
                continue;
            }
            if (project.getTasks()[i].getTaskID().equals(taskID)){
                System.out.println("hi");
                project.getTasks()[i].setStatus(status);
            }
        }
    }

    public void removeTask(String taskId){
        int taskIndex = getTaskIndex(taskId);
        for (int i = taskIndex; i < project.getTasks().length - 1; i++) {
            project.getTasks()[i] = project.getTasks()[i + 1];
        }
        project.getTasks()[project.getTasks().length - 1] = null;
    }

    private int getTaskIndex(String taskId){
        // Find the index of a task based on its ID
        for (int i = 0; i <  project.getTasks().length; i++){
            if (project.getTasks()[i] != null && project.getTasks()[i].getTaskID().equals(taskId)){
                return i;
            }
        }
        return -1;
    }

}


