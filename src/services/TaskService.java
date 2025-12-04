package services;

import models.*;
import utils.Status;


public class TaskService {

    private ProjectService projectService = new ProjectService();

    public Task addTaskToProject(String projectId, String name, Status status) {
        Project project = projectService.filterProjectBYId(projectId);
        Task[] assignedTaskSizeFull = assignTaskSizeIfNull(project.getTasks());
        Task newTask = new Task();
        newTask.setTaskID(project.generateTaskId());
        newTask.setName(name);
        newTask.setStatus(status);
        int nullIndex = getNullIndex(assignedTaskSizeFull);
        assignedTaskSizeFull[nullIndex] = newTask;
        project.setTasks(assignedTaskSizeFull);
        return newTask;
    }

    public Task[] geProjectTasks(String projectId) {
        Project project = projectService.filterProjectBYId(projectId);

        Task[] tasks = project.getTasks();

        if (tasks == null || tasks.length == 0) {
            System.out.println("No task found for the project: " + project.getName());
            return new Task[0];
        }
        return tasks;
    }

    public boolean updateTaskStatus(String projectID, Status status, String taskID){
        Project project = projectService.filterProjectBYId(projectID);
        for (int i = 0; i < project.getTasks().length; i++){
            if (project.getTasks()[i] == null){
                continue;
            }
            if (project.getTasks()[i].getTaskID().equals(taskID)){
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
        System.out.println("You are not allowed to perform this action");
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

    private static int getNullIndex(Task[] assignedTaskSizeFull) {
        for (int i = 0; i < assignedTaskSizeFull.length; i++){
            if (assignedTaskSizeFull[i] == null){
                return i;
            }
        }
        return -1;
    }

    private static Task[] assignTaskSizeIfNull(Task[] tasks){
        if (tasks == null){
            tasks = new Task[5];
        }
        int elementsSize = getElementsSize(tasks);
        if (tasks.length == elementsSize){
            Task[] newTasks = new Task[elementsSize * 2];
            System.arraycopy(tasks, 0, newTasks, 0, newTasks.length);
            return newTasks;
        }
        return tasks;
    }

    private static int getElementsSize(Task[] tasks) {
        int elementsSize = 0;
        for (Task oldTask : tasks) {
            if (oldTask == null) {
                continue;
            }
            elementsSize++;
        }
        return elementsSize;
    }
}


