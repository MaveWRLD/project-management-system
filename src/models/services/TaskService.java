package models.services;

import models.*;
import models.utils.IDGenerator;
import models.utils.Status;


public class TaskService {
    private Project project;

    private ProjectService projectService = new ProjectService();
    private IDGenerator idGenerator= new IDGenerator();


    public Task addTask(String projectId, String name, Status status) {
        Project project = projectService.filterProjectBYId(projectId);
        Task[] tasks = project.getTasks();
        Task[] assignedTaskSizeFull = assignTaskSizeIfNull(tasks);
        Task newTask = new Task();
        newTask.setTaskID(idGenerator.setID('T'));
        newTask.setName(name);
        newTask.setStatus(status);
        for (int i = 0; i <assignedTaskSizeFull.length; i++){
            if (assignedTaskSizeFull[i] == null){
                assignedTaskSizeFull[i] = newTask;
                break;
            }
        }
        project.setTasks(assignedTaskSizeFull);
        return newTask;
    }

    public boolean isCompleted(){
        return project.getTasks()[0].isCompleted();
    }

    public Task[] getTasksForProject(String projectId) {
        Project project = projectService.filterProjectBYId(projectId);
        return project.getTasks();
    }
    
    public void displayTask(String projectId){
        System.out.println("ID  | NAME | Status ");
    }

    public boolean updateTaskStatus(String projectID, Status status, String taskID){
        Project project = projectService.filterProjectBYId(projectID);
        for (int i = 0; i < project.getTasks().length; i++){
            if (project.getTasks()[i] == null){
                continue;
            }
            if (project.getTasks()[i].getTaskID().equals(taskID)){
                System.out.println("hi");
                project.getTasks()[i].setStatus(status);
            }
        }
        return true;
    }

    public boolean removeTask(User user, String projectID, String taskId){
        if (user != null && user.getRole().equals("Admin")){
            System.out.println("Testing User");
            Project project = projectService.filterProjectBYId(projectID);
            int taskIndex = getTaskIndex(projectID, taskId);
            for (int i = taskIndex; i < project.getTasks().length - 1; i++) {
                project.getTasks()[i] = project.getTasks()[i + 1];
            }
            project.getTasks()[project.getTasks().length - 1] = null;
            return true;
        }
        System.out.println("You are not allow to perform this action");
        return false;
    }

    private int getTaskIndex(String projectID, String taskId){
        Project project = projectService.filterProjectBYId(projectID);
        for (int i = 0; i <  project.getTasks().length; i++){
            if (project.getTasks()[i] != null && project.getTasks()[i].getTaskID().equals(taskId)){
                return i;
            }
        }
        return -1;
    }

    private static Task[] assignTaskSizeIfNull(Task[] tasks){
        if (tasks == null){
            tasks = new Task[5];
        }
        int elementsSize = 0;
        for (Task oldTask : tasks) {
            if (oldTask == null) {
                continue;
            }
            elementsSize++;
        }
        if (tasks.length == elementsSize){
            Task[] newTasks = new Task[elementsSize * 2];
            for (int i = 0; i < newTasks.length; i++){
                newTasks[i] = tasks[i];
            }
            return newTasks;
        }
        return tasks;
    }

}


