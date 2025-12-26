package services;

import models.*;
import utils.Status;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;

import java.util.ArrayList;
import java.util.Collection;


public class TaskService {

    private ProjectService projectService;

    /**
     * Instantiates a new Task service.
     *
     * @param projectService the project service
     */
    public TaskService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Creates a new {@link Task} instance and associates it with the specified project.
     *
     * <p>This method initializes a new task by generating a unique task ID from the given
     * {@link Project}, setting its name, and assigning the provided {@link Status}.
     *
     * @return a fully initialized {@link Task} instance.
     */
    public Task createTask(Project project, String name, Status status) {
        Task newTask = new Task();
        newTask.setTaskID(project.generateTaskId());
        newTask.setName(name);
        newTask.setStatus(status);
        return newTask;
    }

    /**
     * Retrieves the tasks associated with a given {@link Project}.
     *
     * <p>This method returns the project's internal task array if it contains at least one
     * non-{@code null} {@link Task}.
     */
    public Collection<Task> getProjectTasks(Project project){
        var projectTaskMap = projectService.projectTaskMap();
        if (!projectTaskMap.containsKey(project))
            projectTaskMap.put(project, new ArrayList<>());
        return projectTaskMap.get(project);
    }

    /**
     * Adds a new {@link Task} to the specified project's task list.
     *
     * <p>This method locates the {@link Project} by its unique identifier, ensures the project's
     * task array has sufficient capacity (resizing it if full via
     */
    public void addTaskToProject(String projectId, String name, Status status) {
        try {
            Project project = projectService.filterProjectBYId(projectId);
            Task newTask = createTask(project, name, status);
            getProjectTasks(project).add(newTask);
        } catch (ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the status of a specific {@link Task} within a given {@link Project}.
     *
     * <p>This method locates the target project by its unique identifier, retrieves its tasks,
     * finds the task with the specified {@code taskID}, and sets its {@link Status} to the
     * provided value.</p>
     *
     * @throws TaskNotFoundException    if no task with the given {@code taskID} exists within the project.
     * @throws EmptyProjectException    if the project has no tasks or tasks cannot be retrieved.
     * @throws ProjectNotFoundException if no project with the given {@code projectID} exists.
     */
    public void updateTaskStatus(String projectID, Status status, String taskID)
            throws TaskNotFoundException, EmptyProjectException, ProjectNotFoundException {
        Project project = projectService.filterProjectBYId(projectID);
        Collection<Task> tasks = getProjectTasks(project);
        if (tasks.isEmpty()) {
            throw new EmptyProjectException(
                    "Project with id " + project.getId() + " has no tasks associated with it"
            );
        }
        Task task = getTask(tasks, taskID);
        task.setStatus(status);
    }


    /**
     * Retrieves the index of a specific {@link Task} within a given task array.
     *
     * <p>This method iterates through the provided {@link Task} array and returns the index
     * of the first task whose ID matches the specified {@code taskId}. If no matching task
     * is found, a {@link TaskNotFoundException} is thrown.</p>
     *
     * @return the zero-based index of the matching task within the array.
     */
    public Task getTask(Collection<Task> tasks, String taskId) throws TaskNotFoundException {
        for (Task task : tasks) {
            if (task != null && task.getTaskID().equals(taskId)) {
                return task;
            }
        }
        throw new TaskNotFoundException(taskId);
    }
}
