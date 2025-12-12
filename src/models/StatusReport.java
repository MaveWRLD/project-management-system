package models;

public class StatusReport {
    private String projectID;
    private String projectName;
    private int totalTask;
    private int completedTasks;
    private float completionPercentage;

    public StatusReport(String projectID, String projectName, int totalTask, int completedTasks,
            float completionPercentage) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.totalTask = totalTask;
        this.completedTasks = completedTasks;
        this.completionPercentage = completionPercentage;
    }
    

    public int getTotalTask() {
        return totalTask;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public float getCompletionPercentage() {
        return completionPercentage;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }
}
