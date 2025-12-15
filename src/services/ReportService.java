package services;

import models.Project;
import models.StatusReport;
import models.Task;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;

public class ReportService {
    private final TaskService taskService;
    private ProjectService projectService;

    /**
     * Instantiates a new Report service.
     *
     * @param taskService    the task service
     * @param projectService the project service
     */
    public ReportService(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }


    /**
     * Generates status reports for the given list of projects.
     *
     * <p>This method iterates over the provided {@link Project} array and builds a corresponding
     * {@link StatusReport} for each non-{@code null} project. Each report aggregates task metrics
     * for the project, including:
     * The method handles empty project task lists via {@link EmptyProjectException} (logged),
     * and propagates {@link ProjectNotFoundException} if the underlying aggregation utilities
     * require a valid project identifier that cannot be found.</p>
     *
     * @throws ProjectNotFoundException if computing metrics for a project fails due to an unknown project ID.
     * @see Project
     * @see Task
     * @see StatusReport
     * @see EmptyProjectException
     * @see ProjectNotFoundException
     */
    public StatusReport[] generateReport(Project[] projects) throws ProjectNotFoundException {
        StatusReport[] reports = new StatusReport[100];
        try {
            int i = 0;
            for (Project project : projects) {
                if (project != null) {
                    for (Task task : project.getTasks())
                        if (task != null) {
                            reports[i] = new StatusReport(
                                    project.getId(),
                                    project.getName(),
                                    totalTask(project.getId()),
                                    completedTasks(project.getId()),
                                    completionPercentage(project.getId())
                            );
                        }
                }
                ++i;
            }
        } catch (EmptyProjectException e) {
            System.out.println(e.getMessage());
        }
        return reports;

    }


    /**
     * Calculates the total number of tasks associated with a specific project.
     *
     * <p>This method retrieves the {@link Project} using its unique identifier and then
     * obtains all tasks linked to that project. It counts only non-{@code null} tasks
     * and returns the total count.</p>
     *
     * @throws EmptyProjectException    if the project has no tasks or is considered empty.
     * @throws ProjectNotFoundException if no project with the given projectId exists.
     * @see Project
     * @see Task
     * @see EmptyProjectException
     * @see ProjectNotFoundException
     */
    public int totalTask(String projectId) throws EmptyProjectException, ProjectNotFoundException {
        int totalTask = 0;
        Project project = projectService.filterProjectBYId(projectId);
        Task[] tasks = taskService.getProjectTasks(project);
        for (Task task : tasks) {
            if (task != null) {
                ++totalTask;
            }
        }
        return totalTask;

    }


    /**
     * Calculates the number of completed tasks for a specific project.
     *
     * <p>This method retrieves the {@link Project} by its unique identifier and then
     * obtains all associated {@link Task} objects. It counts only tasks that are
     * non-{@code null} and marked as completed.</p>
     *
     * @see Project
     * @see Task
     * @see EmptyProjectException
     * @see ProjectNotFoundException
     */
    public int completedTasks(String projectId) {
        int completed = 0;
        try {
            Project project = projectService.filterProjectBYId(projectId);
            Task[] tasks = taskService.getProjectTasks(project);
            for (Task task : tasks) {
                if (task != null && task.isCompleted()) {
                    ++completed;
                }
            }
        } catch (EmptyProjectException | ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return completed;

    }


    /**
     * Calculates the completion percentage for a project's tasks.
     *
     * <p>This method computes the percentage of completed tasks for the specified
     * {@link Project} by dividing the number of completed tasks by the total number
     * of tasks and multiplying by 100. If either the number of completed tasks or
     * the total number of tasks * the total number of tasks is zero, the method returns {@code 0} to avoid
     * division by zero.</p>
     *
     * @throws EmptyProjectException    if the project has no tasks or is considered empty.
     * @throws ProjectNotFoundException if no project with the given {@code projectId} exists.
     * @see Project
     * @see Task
     */
    public float completionPercentage(String projectId) throws EmptyProjectException, ProjectNotFoundException {
        float completed = completedTasks(projectId);
        float totalTasks = totalTask(projectId);
        if (completed == 0 || totalTasks == 0) {
            return 0f;
        }
        return (completed / totalTasks) * 100f;

    }


    /**
     * Calculates the average completion percentage across the provided projects.
     *
     * <p>This method iterates through the given array of {@link Project} objects and, for each
     * non-{@code null} project, computes its completion percentage via {@link #completionPercentage(String)}.
     * The average is calculated as the sum of per-project completion percentages divided by the number
     * of projects considered.</p>
     *
     * @throws ProjectNotFoundException if computing a project's completion requires a project ID that cannot be found.
     * @see Project
     * @see Task
     * @see EmptyProjectException
     * @see ProjectNotFoundException
     */
    public float completionAverage(Project[] projects) throws ProjectNotFoundException {
        float totalPercentageCount = 0;
        float sumOfPercentages = 0;
        try {
            for (Project project : projects) {
                if (project != null) {
                    for (Task task : project.getTasks())
                        if (task != null) {
                            float percent = completionPercentage(project.getId());
                            sumOfPercentages += percent;
                            totalPercentageCount++;
                        }
                }
            }
            if (totalPercentageCount == 0) return 0;
        } catch (EmptyProjectException e) {
            System.out.println(e.getMessage());
        }
        return sumOfPercentages / totalPercentageCount;
    }
}
