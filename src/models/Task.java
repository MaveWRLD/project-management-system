package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import interfaces.Identifiable;
import utils.Status;

public class Task implements Identifiable {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    @JsonIgnore
    @Override
    public String getId() {
        return "";
    }
}

