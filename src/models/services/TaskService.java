package models.services;

import models.*;
import models.utils.Status;

import java.text.NumberFormat;


public class TaskService {

    Task task = new Task();

    public void displayTask(Project project){
        project.displayTask();
    }

    public boolean isCompleted(Project project){
        return project.getTasks()[0].isCompleted();
    }

    public float calculateCompletionPercentage(Project project){
        int completed = 0;
        int totalTasks = 0;
        for (Task task : project.getTasks()){
            if (task == null){
                continue;
            }
            totalTasks++;

            if (task.getStatus().equals(Status.COMPLETED)){
                completed++;
            }
        }

        return (float) (completed / totalTasks) * 100;
    }
    public void updateTaskStatus (Status status, String taskID, Project project){
        project.updateTaskStatus(status, taskID);
    }

    public void removeTask (String taskId, Project project){
        project.removeTask(taskId);
    }
}

