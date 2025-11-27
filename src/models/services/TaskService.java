package models.services;

import models.Project;
import models.Task;

import java.util.Objects;

public class TaskService {

    //Task task = new Task();
    public void addTask(String id, String name, String status, Project project){
        new Task(id, name, status);
    }

    public void updateTaskStatus(String status){

    }

//    public void updateTaskStatus(String taskID, String status, Project project){
//        for (Task task : getProjectTasks(project)) {
//            if (task == null){
//                continue;
//            }
//            if (Objects.equals(task.getId(), taskID)){
//                task.setStatus(status);
//            }
//        }
//        System.out.println("updating ....");
//    }

//    public void removeTask(String id, Project project){
//        int taskIndex = getTaskIndex(Task.getProjectTasks(project), id);
//        for (int i = taskIndex; i < Task.getProjectTasks(project).length - 1; i++) {
//            Task.getProjectTasks(project)[i] = Task.getProjectTasks(project)[i + 1];
//        }
//        Task.getProjectTasks(project)[Task.getProjectTasks(project).length - 1] = null;
//    }

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
