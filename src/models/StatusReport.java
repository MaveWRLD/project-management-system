package models;

public class StatusReport {
    private final String projectID;
    private final String projectName;
    private final int totalTask;
    private final int completedTasks;
    private final float completionPercentage;

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
