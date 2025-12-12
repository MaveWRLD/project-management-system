package services;

import models.Project;
import models.StatusReport;
import models.Task;
import utils.exceptions.EmptyProjectException;
import utils.exceptions.ProjectNotFoundException;

public class ReportService {
    private final TaskService taskService;
    private ProjectService projectService;

    public ReportService(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    public StatusReport[] generateReport(Project[] projects) throws ProjectNotFoundException {
        StatusReport[] reports = new StatusReport[100];
        try {

        int i = 0;
        for (Project project : projects){
            if (project != null){
               for (Task task : project.getTasks())
                    if (task != null){
                        reports[i] = new StatusReport(project.getId(), project.getName(), totalTask(project.getId()), completedTasks(project.getId()),
                                completionPercentage(project.getId()));
                    }
               }
            ++i;
        }
        } catch ( EmptyProjectException e) {
            System.out.println(e.getMessage());
        }
        return reports;
    }

    public int totalTask(String projectId) throws EmptyProjectException, ProjectNotFoundException {
        int totalTask = 0;
        Project project = projectService.filterProjectBYId(projectId);
        Task[] tasks = taskService.getProjectTasks(project);
        for (Task task : tasks){
            if (task != null) {
                ++totalTask;
            }
        }
        return  totalTask;
    }

    public int completedTasks(String projectId)  {
        int completed = 0;
        try {
            Project project = projectService.filterProjectBYId(projectId);
            Task[] tasks= taskService.getProjectTasks(project);
            for (Task task : tasks){
                if (task != null && task.isCompleted()){
                    ++completed;
                }
            }
        } catch (EmptyProjectException | ProjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return completed;
    }

    public float completionPercentage(String projectId) throws EmptyProjectException, ProjectNotFoundException {
        float completed = completedTasks(projectId);
        float totalTasks = totalTask(projectId);
        if (completed == 0 || totalTasks == 0)
            return 0;
        return (completed / totalTasks) * 100;
    }


    public float completionAverage(Project[] projects) throws ProjectNotFoundException {
        float totalPercentageCount = 0;
        float sumOfPercentages = 0;
        try {
            for (Project project : projects) {
                if (project != null){
                for (Task task : project.getTasks())
                    if (task != null){
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
