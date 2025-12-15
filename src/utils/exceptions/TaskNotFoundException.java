
package utils.exceptions;

/**
 * Exception thrown when a Task with the specified ID cannot be found.
 *
 * <p>This custom exception is used to indicate that an operation requiring a valid task
 * reference failed because the task does not exist in the system. It extends {@link Exception}
 * and provides a constructor that includes the missing task's ID in the error message for clarity.</p>
 */
public class TaskNotFoundException extends Exception {
    /**
     * Constructs a new {@code TaskNotFoundException} with a detail message
     *
     * @param taskId the unique identifier of the task that was not found; must not be {@code null}.
     */
    public TaskNotFoundException(String taskId) {
        super("Task with ID " + taskId + " does not exist");
    }
}

