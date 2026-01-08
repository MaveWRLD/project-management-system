package models;

import utils.Status;

public class Task {
    private String taskID;
    private String name;
    private Status status;

    public Task(String taskID, String name, Status status) {
        this.taskID = taskID;
        this.name = name;
        this.status = status;
    }

    public Task() {
    }

    public String getTaskID() {
        return taskID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public synchronized Status getStatus() {
        return status;
    }

    public synchronized void setStatus(Status status) {
        this.status = status;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
}

