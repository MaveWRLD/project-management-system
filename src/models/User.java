package models;

import utils.IDGenerator;
import utils.exceptions.TaskNotFoundException;

public abstract class User {
    private String id;
    private String name;
    private String email;

    public User(String name, String email) {
        IDGenerator idGenerator = new IDGenerator();
        this.id = idGenerator.setID('U');
        this.name = name;
        this.email = email;
    }

    public void removeTask(String projectID, String taskId) throws TaskNotFoundException {
        throw new UnsupportedOperationException("You are not allowed to perform this action");
    };

    public String getName() {
        return name;
    }

    public abstract String getRole();

    public String getEmail() {
        return email;
    }
}
