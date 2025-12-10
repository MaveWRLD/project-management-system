package utils.exceptions;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(String projectId, String taskId) {
        super("Project with id " + projectId + " has no associated task with id " + taskId);
    }
}
