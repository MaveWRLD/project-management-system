package models;

import utils.ID;
import utils.exceptions.TaskNotFoundException;

public abstract class User {
    private String id;
    private String name;
    private String email;

    private final ID idGenerator = new ID();
    private static int userCounter = 1;

    public User(String name, String email) {
        this.id = idGenerator.generateProjectId('U', userCounter++);
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
