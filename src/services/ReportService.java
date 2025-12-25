package services;

import interfaces.Completable;
import models.Project;
import models.StatusReport;
import models.Task;
import utils.Status;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ReportService {
    private final TaskService taskService;

    /**
     * Instantiates a new Report service.
     *
     * @param taskService the task service
     */
    public ReportService(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * Generates status reports for the given list of projects.
     *
     * <p>This method iterates through the given map of {@link Project}objects and collection {@link Task} objects,
     * for each project key and builds a corresponding {@link StatusReport}.
     * Each report aggregates task metrics for the project.
     * The method handles empty project task lists via {@link EmptyProjectException} (logged)
     * </p>
     *
     * @see Project
     * @see Task
     * @see StatusReport
     * @see EmptyProjectException
     */
    public Collection<StatusReport> generateReport(Map<Project, Collection<Task>> projectTaskMap) {
        Collection<StatusReport> reports = new ArrayList<>();
        try {
            for (Project project : projectTaskMap.keySet()) {
                reports.add(new StatusReport(
                                project.getId(),
                                project.getName(),
                                totalTask(project),
                                completedTasks(project),
                                completionPercentage(project)
                        )
                );
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
     * @see Project
     * @see Task
     */
    public int totalTask(Project project){
        return taskService.getProjectTasks(project).size();
    }

    public boolean isCompleted(Completable completable) {
        return completable.isCompleted();
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
     * @see ProjectNotFoundException
     */
    public int completedTasks(Project project) {
        int completed = 0;
        Collection<Task> tasks = taskService.getProjectTasks(project);
        for (Task task : tasks) {
            if (isCompleted(() -> task.getStatus().equals(Status.COMPLETED))) {
                ++completed;
            }
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
     * @see Project
     * @see Task
     */
    public float completionPercentage(Project project) throws EmptyProjectException {
        float completed = completedTasks(project);
        float totalTasks = totalTask(project);
        if (completed == 0 || totalTasks == 0) {
            return 0f;
        }
        return (completed / totalTasks) * 100f;
    }

    /**
     * Calculates the average completion percentage across the provided projects.
     *
     * <p>This method iterates through the given map of {@link Project} objects and collection {@link Task} objects, for each
     * project key, computes its completion percentage via {@link #completionPercentage(Project)}.
     * The average is calculated as the sum of per-project completion percentages divided by the number
     * of projects considered.</p>
     *
     * @see Project
     * @see Task
     */
    public float completionAverage(Map<Project, Collection<Task>> projectTaskMap){
        float totalPercentageCount = 0;
        float sumOfPercentages = 0;
        try {
            for (Project project : projectTaskMap.keySet()) {
                    float percent = completionPercentage(project);
                    sumOfPercentages += percent;
                    totalPercentageCount++;
            }
            if (totalPercentageCount == 0) return 0;
        } catch (EmptyProjectException e) {
            System.out.println(e.getMessage());
        }
        return sumOfPercentages / totalPercentageCount;
    }
}
