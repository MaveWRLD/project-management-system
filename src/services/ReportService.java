package services;

import interfaces.Completable;
import models.Project;
import models.StatusReport;
import models.Task;
import utils.Status;
import utils.exceptions.ProjectNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ReportService {
    private TaskService taskService;
    private ProjectService projectService;

    /**
     * Instantiates a new Report service.
     *
     * @param taskService the task service
     */
    public ReportService(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }


    /**
     * Generates status reports for the given list of projects.
     *
     * <p>This method iterates through the given map of {@link Project}objects and collection {@link Task} objects,
     * for each project key and builds a corresponding {@link StatusReport}.
     * Each report aggregates task metrics for the project.
     * </p>
     *
     * @see Project
     * @see Task
     * @see StatusReport
     */
    public Collection<StatusReport> generateReport(Map<String, Project> project) {
        Collection<StatusReport> reports = new ArrayList<>();
        for (String key : project.keySet()){
            reports.add(new StatusReport(
                            key,
                            projectService.filterProjectBYId(key).getName(),
                            totalTask(key),
                            completedTasks(key),
                            completionPercentage(key)
                    )
            );
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
    public int totalTask(String projectId){
        return taskService.getProjectTasks(projectId).size();
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
    public int completedTasks(String projectId) {
        int completed = 0;
        Collection<Task> tasks = taskService.getProjectTasks(projectId);
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
     * @see Project
     * @see Task
     */
    public float completionPercentage(String projectId) {
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
     * <p>This method iterates through the given map of {@link Project} objects and collection {@link Task} objects, for each
     * project key, computes its completion percentage via {@link #completionPercentage(String)}.
     * The average is calculated as the sum of per-project completion percentages divided by the number
     * of projects considered.</p>
     *
     * @see Project
     * @see Task
     */
    public float completionAverage(Map<String, Project> projectMap){
        float totalPercentageCount = 0;
        float sumOfPercentages = 0;

        for (String key : projectMap.keySet()){
                float percent = completionPercentage(key);
                sumOfPercentages += percent;
                totalPercentageCount++;
        }
        if (totalPercentageCount == 0) return 0;
        return sumOfPercentages / totalPercentageCount;
    }
}
