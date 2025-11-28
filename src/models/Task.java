package models;


import models.interfaces.Completable;
import models.utils.IDGenerator;
import models.utils.Status;

public class Task implements Completable {
    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    private String taskID;
    private String name;
    private Status status;

    private IDGenerator idGenerator= new IDGenerator();

    public Task createTask(String name, Status status){
        Task task = new Task();
        setTaskID(idGenerator.setID('T'));
        setName(name);
        setStatus(status);
        return task;
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

    @Override
    public boolean isCompleted() {
        return status == Status.COMPLETED;
    }
}

