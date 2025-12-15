package services;

import models.*;
import utils.ResizeObjectSizeUtils;
import utils.Status;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;
import utils.exceptions.TaskNotFoundException;


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
     * Adds a new {@link Task} to the specified project's task list.
     *
     * <p>This method locates the {@link Project} by its unique identifier, ensures the project's
     * task array has sufficient capacity (resizing it if full via
     * {@link ResizeObjectSizeUtils#resizeObjectsSizeIfFull(Object[])}), creates a new task using
     * the provided name and {@link Status}, inserts it at the first available ({@code null}) index,
     * and updates the project with the modified task array.</p>
     */
    public void addTaskToProject(String projectId, String name, Status status) {
        try {
            Project project = projectService.filterProjectBYId(projectId);
            Task[] tasks = project.getTasks();
            tasks = ResizeObjectSizeUtils.resizeObjectsSizeIfFull(tasks);
            int nullIndex = getNullIndex(tasks);
            Task newTask = createTask(project, name, status);
            tasks[nullIndex] = newTask;
            project.setTasks(tasks);
        } catch (ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
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
        Task[] tasks = getProjectTasks(project);
        Task task = getTask(tasks, taskID);
        task.setStatus(status);
    }


    /**
     * Retrieves the tasks associated with a given {@link Project}.
     *
     * <p>This method returns the project's internal task array if it contains at least one
     * non-{@code null} {@link Task}. If all entries are {@code null} (i.e., the project has
     * no tasks), an {@link EmptyProjectException} is thrown.</p>
     */
    public Task[] getProjectTasks(Project project) throws EmptyProjectException {
        Task[] tasks = project.getTasks();
        int foundElements = 0;
        for (Task task : tasks) {
            if (task != null) {
                foundElements++;
            }
        }
        if (foundElements == 0) {
            throw new EmptyProjectException(
                    "Project with id " + project.getId() + " has no tasks associated with it"
            );
        }
        return tasks;
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
    public int getTaskIndex(Task[] tasks, String taskId) throws TaskNotFoundException {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null && tasks[i].getTaskID().equals(taskId)) {
                return i;
            }
        }
        throw new TaskNotFoundException(taskId);
    }


    /**
     * Retrieves a specific {@link Task} from the given task array by its unique identifier.
     *
     * <p>This method uses {@link #getTaskIndex(Task[], String)} to find the index of the task
     * with the specified {@code taskId} and then returns the corresponding {@link Task} object.
     * If no matching task is found, a {@link TaskNotFoundException} is thrown.</p>
     *
     * @return the {@link Task} instance matching the given {@code taskId}.
     */
    public Task getTask(Task[] tasks, String taskId) throws TaskNotFoundException {
        int taskIndex = getTaskIndex(tasks, taskId);
        return tasks[taskIndex];
    }


    /**
     * Finds the index of the first {@code null} element in the given task array.
     *
     * <p>This method iterates through the provided {@link Task} array and returns the index
     * of the first position that contains {@code null}. If no {@code null} elements are found,
     * it returns {@code -1}.</p>
     *
     * @return the index of the first {@code null} element, or {@code -1} if none exist.
     */
    private static int getNullIndex(Task[] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
