package services;

import models.*;
import utils.ResizeObjectSizeUtils;
import utils.Status;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;


public class TaskService {

    private ProjectService projectService;

    public TaskService(ProjectService projectService) {
        this.projectService = projectService;
    }


    public void addTaskToProject(String projectId, String name, Status status){
        try {
        Project project = projectService.filterProjectBYId(projectId);
        Task[] tasks = project.getTasks();
        tasks = ResizeObjectSizeUtils.resizeObjectsSizeIfFull(tasks);
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


    public void updateTaskStatus(String projectID, Status status, String taskID)
            throws TaskNotFoundException, EmptyProjectException, ProjectNotFoundException {

        Project project = projectService.filterProjectBYId(projectID);
        Task[] tasks = getProjectTasks(project);
        Task task = getTask(tasks, taskID);
        task.setStatus(status);
    }

    public Task[] getProjectTasks(Project project) throws EmptyProjectException {
        Task[] tasks = project.getTasks();
        int noElement = 0;
        int foundElements = 0;
        for (Task task : tasks) {
            if (task != null) {
                foundElements++;
            }
        }
        if (foundElements == noElement){
            throw new EmptyProjectException(project.getId() + " with id " + "has no tasks associated with it");
        }
        return tasks;
    }

    public int getTaskIndex(Task[] tasks, String taskId) throws TaskNotFoundException {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null && tasks[i].getTaskID().equals(taskId)) {
                return i;
            }
        }
        throw new TaskNotFoundException(taskId);
    }

    public Task getTask(Task[] tasks, String taskId) throws TaskNotFoundException {
       int taskIndex = getTaskIndex(tasks, taskId);
       return tasks[taskIndex];
    }

    private static int getNullIndex(Task[] tasks) {
        for (int i = 0; i < tasks.length; i++){
            if (tasks[i] == null){
                return i;
            }
        }
        return -1;
    }
}
