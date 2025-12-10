package services;

import models.*;
import utils.IDGenerator;
import utils.ResizeUtils;
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
        Project project;
        Task[] tasks = null;
        try {
            project = projectService.filterProjectBYId(projectId);
            tasks = project.getTasks();
            if (tasks == null || tasks.length == 0) {
                throw new EmptyProjectException(project.getName() + " with ID " + project.getId() + " has no associated tasks");
        }
        } catch (ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public void updateTaskStatus(String projectID, Status status, String taskID)
            throws TaskNotFoundException, EmptyProjectException, ProjectNotFoundException {

        Project project = projectService.filterProjectBYId(projectID);
        Task[] tasks = getProjectTasks(projectID);
        int taskIndex = getTaskIndex(project, taskID);
        tasks[taskIndex].setStatus(status);
    }


    public int getTaskIndex(Project project, String taskId) throws TaskNotFoundException {
        try {
            Task[] tasks = getProjectTasks(project.getId());
            for (int i = 0; i < tasks.length; i++) {
                if (tasks[i] != null && tasks[i].getTaskID().equals(taskId)) {
                    return i;
                }
            }
        } catch ( EmptyProjectException e){
            System.out.println(e.getMessage());
        }
        throw new TaskNotFoundException(project.getId(), taskId);
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
        return ResizeUtils.resizeProjectsSizeIfFull(tasks);
    }
}
