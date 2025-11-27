package models.services;

import models.Task;

import java.util.Arrays;
import java.util.Objects;

public class TaskService {
    public static void addTask(String id, String name, String status){
        new Task(id, name, status);
    }

    public static void updateTaskStatus(String id, String status){
        for (Task task : Task.getAllTask()) {
            if (task == null){
                continue;
            }
            System.out.println(task.getName());
            if (Objects.equals(task.getId(), id)){
                System.out.println(Objects.equals(task.getId(), id));
                task.setStatus(status);
            }
        }
        System.out.println("updating ....");
    }

    public static void removeTask(String id){
        int taskIndex = getArrIndex(Task.getAllTask(), id);
        for (int i = taskIndex; i < Task.getAllTask().length - 1; i++) {
            Task.getAllTask()[i] = Task.getAllTask()[i + 1];
        }
        Task.getAllTask()[Task.getAllTask().length - 1] = null;
    }

    public static int getArrIndex(Task[] task, String id){
        for (int i = 0; i < task.length; i++){
            if (task[i] != null && task[i].getId().equals(id)){
                return i;
            }
        }
        return -1;
    }
}
