package models;
// Abstract base class

import utils.IDGenerator;
import utils.exceptions.TaskNotFoundException;

public abstract class User {
    private String id;
    private String name;
    private String email;


    private IDGenerator idGenerator = new IDGenerator();

    public User(String name, String email) {
        this.id = idGenerator.setID('U');
        this.name = name;
        this.email = email;
    }

    public void login(){
    }

    public void removeTask(String projectID, String taskId) throws TaskNotFoundException {
        throw new UnsupportedOperationException("You are not allowed to perform this action");
    };

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public IDGenerator getIdGenerator() {
        return idGenerator;
    }

    public abstract String getRole();

}
