package models;


import models.interfaces.Completable;
import models.utils.IDGenerator;
import models.utils.Status;

public class Task implements Completable {
    private String taskID;
    private String name;
    private Status status;

    public String getTaskID() {
        return taskID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
    
    @Override
    public boolean isCompleted() {
        return status == Status.COMPLETED;
    }
}

