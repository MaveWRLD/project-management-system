package models.services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import models.Task;


public class TaskService {


    public void displayTask(SoftwareProject softwareProject){
        System.out.println("ID  | NAME | Status ");
        for (Task task : softwareProject.getTasks()) {
            if (task != null){
                System.out.println(task.getId() + " | " + task.getName() + " | " + task.getStatus());
            }
        }
        
    }

    public void createTask(String id, String name, String status, Project project){
        new Task(id, name, status);
    }

    public void updateTaskStatus(String status, String taskID, SoftwareProject project){
        for (int i = 0; i < project.getTasks().length; i++){
                if (project.getTasks()[i] == null){
                    continue;
                }
                if (project.getTasks()[i].getId().equals(taskID)){
                    project.getTasks()[i].setStatus(status);
                }
        }
    }

    public void updateTaskStatus(String status, String taskID, HardwareProject project){
        for (int i = 0; i < project.getTasks().length; i++){
                if (project.getTasks()[i] == null){
                    continue;
                }
                if (project.getTasks()[i].getId().equals(taskID)){
                    project.getTasks()[i].setStatus(status);
                }
        }
    }

    public void removeTask(String taskId, SoftwareProject project){
        int taskIndex = getTaskIndex(project.getTasks(), taskId);
        for (int i = taskIndex; i < project.getTasks().length; i++) {
            project.getTasks()[i] = project.getTasks()[i + 1];
        }
        project.getTasks()[project.getTasks().length - 1] = null;
    }

    public void removeTask(String taskId, HardwareProject project){
        int taskIndex = getTaskIndex(project.getTasks(), taskId);
        for (int i = taskIndex; i < project.getTasks().length; i++) {
            project.getTasks()[i] = project.getTasks()[i + 1];
        }
        project.getTasks()[project.getTasks().length - 1] = null;
    }

    private int getTaskIndex(Task[] task, String taskId){
        // Find the index of a task based on its ID
        for (int i = 0; i < task.length; i++){
            if (task[i] != null && task[i].getId().equals(taskId)){
                return i;
            }
        }
        return -1;
    }

}
