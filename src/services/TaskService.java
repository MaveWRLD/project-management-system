package services;

import models.*;
import utils.Status;


public class TaskService {

    private ProjectService projectService = new ProjectService();

    public void addTaskToProject(String projectId, String name, Status status){
        Project project = projectService.filterProjectBYId(projectId);
        Task[] tasks = assignTaskSizeIfNull(project.getTasks());
        int nullIndex = getNullIndex(tasks);
        Task newTask = createTask(project, name, status);
        tasks[nullIndex] = newTask;
        project.setTasks(tasks);
    }

    public Task createTask(Project project, String name, Status status) {
        Task newTask = new Task();
        newTask.setTaskID(project.generateTaskId());
        newTask.setName(name);
        newTask.setStatus(status);
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

    public void updateTaskStatus(String projectID, Status status, String taskID){
        Project project = projectService.filterProjectBYId(projectID);
        Task[] tasks = getProjectTasks(projectID);
        int taskIndex = getTaskIndex(project, taskID);
        tasks[taskIndex].setStatus(status);
    }

    public int getTaskIndex(Project project, String taskId){
        for (int i = 0; i <  project.getTasks().length; i++){
            if (project.getTasks()[i] != null && project.getTasks()[i].getTaskID().equals(taskId)){
                return i;
            }
        }
        return -1;
    }

    private static int getNullIndex(Task[] tasks) {
        for (int i = 0; i < tasks.length; i++){
            if (tasks[i] == null){
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


