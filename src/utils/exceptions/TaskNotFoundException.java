package utils.exceptions;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(String taskId) {
        super(" Task with " + taskId + " does not exist");
    }
}
