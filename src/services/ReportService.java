package services;

import models.Project;
import models.StatusReport;
import models.Task;

public class ReportService {

    public StatusReport[] generateReport(Project[] projects){
        StatusReport[] reports = new StatusReport[100];
        int i = 0;
        for (Project project : projects){
            if (project == null){
                continue;
            }
            for (Task task : project.getTasks())
                if (task != null){
                    reports[i] = new StatusReport(project.getId(), project.getName(), totalTask(project), completedTasks(project), completionPercentage(project));
                }
            ++i;
        }
        return reports;
    }

    public int totalTask(Project project){
        int totalTask = 0;
        for (Task task : project.getTasks()){
            if (task == null){
                continue;
            }
            ++totalTask;
        }
        return  totalTask;
    }

    public int completedTasks(Project project){
        int completed = 0;
        for (Task task : project.getTasks()){
            if (task == null){
                continue;
            }
            if (task.isCompleted()){
                ++completed;
            }
        }
        return completed;
    }

    public float completionPercentage(Project project){
        float completed = completedTasks(project);
        float totalTasks = totalTask(project);
        if (completed == 0 || totalTasks == 0)
            return 0;
        return (completed / totalTasks) * 100;
    }

    public float completionAverage(Project[] projects) {
        float totalPercentageCount = 0;
        float sumOfPercentages = 0;
        for (Project project : projects) {
            if (project == null) continue;
            for (Task task : project.getTasks())
                if (task != null){
                float percent = completionPercentage(project);
                    sumOfPercentages += percent;
                    totalPercentageCount++;
                }
        }
        if (totalPercentageCount == 0) return 0;
        return sumOfPercentages / totalPercentageCount;
    }

}
