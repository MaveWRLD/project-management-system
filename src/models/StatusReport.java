package models;

public class StatusReport {
    private String projectID;
    private String projectName;
    private int totalTask;
    private int completedTasks;
    private float completionPercentage;
    private float completionAverage;

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

    public float getCompletionAverage() {
        return completionAverage;
    }


    public String getProjectID() {
        return projectID;
    }


    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }


    public String getProjectName() {
        return projectName;
    }


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
}
