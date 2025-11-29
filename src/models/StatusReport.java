package models;

public class StatusReport {
    private String projectID;
    private String projectName;
    private int totalTask;
    private int completedTasks;
    private float completionPecentage;
    private float completionAverage;

    public StatusReport(String projectID, String projectName, int totalTask, int completedTasks,
            float completionPecentage) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.totalTask = totalTask;
        this.completedTasks = completedTasks;
        this.completionPecentage = completionPecentage;
    }
    

    public int getTotalTask() {
        return totalTask;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public float getCompletionPecentage() {
        return completionPecentage;
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
