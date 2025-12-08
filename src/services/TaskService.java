package services;

import models.*;
import utils.Status;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;


public class TaskService {

    private ProjectService projectService = new ProjectService();

    public void addTaskToProject(String projectId, String name, Status status){
        try {
        Project project = projectService.filterProjectBYId(projectId);
        Task[] tasks = assignTaskSizeIfNull(project.getTasks());
        int nullIndex = getNullIndex(tasks);
        Task newTask = createTask(project, name, status);
        tasks[nullIndex] = newTask;
        project.setTasks(tasks);
        } catch (ProjectNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public Task createTask(Project project, String name, Status status) {
        Task newTask = new Task();
        newTask.setTaskID(project.generateTaskId());
        newTask.setName(name);
        newTask.setStatus(status);
        return newTask;
    }

    public Task[] getProjectTasks(String projectId) throws EmptyProjectException {
        Project project = null;
        Task[] tasks = null;
        try {
            project = projectService.filterProjectBYId(projectId);
            tasks = project.getTasks();
            if (tasks == null || tasks.length == 0) {
                throw new EmptyProjectException(project.getName() + " with " + project.getId() + " has no associated tasks");
        }
        } catch (ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public void updateTaskStatus(String projectID, Status status, String taskID) throws TaskNotFoundException{
        try {
        Project project = projectService.filterProjectBYId(projectID);
        Task[] tasks = getProjectTasks(projectID);
        int taskIndex = getTaskIndex(project, taskID);
        tasks[taskIndex].setStatus(status);
        } catch (ProjectNotFoundException | EmptyProjectException e){
            System.out.println(e.getMessage());
        }
    }

    public int getTaskIndex(Project project, String taskId) throws TaskNotFoundException {
        try {
            Task[] tasks = getProjectTasks(project.getId());
            for (int i = 0; i < project.getTasks().length; i++) {
                if (project.getTasks()[i] != null && project.getTasks()[i].getTaskID().equals(taskId)) {
                    return i;
                }
            }
        } catch ( EmptyProjectException e){
            System.out.println(e.getMessage());
        }
        throw new TaskNotFoundException( "Project with id " + project.getId() + " has no associated task with id " + taskId);
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
            if (oldTask != null) {
                elementsSize++;
            }
        }
        return elementsSize;
    }
}


