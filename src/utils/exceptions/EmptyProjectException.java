package utils.exceptions;

/**
 * Exception thrown when a Project is found to have no associated tasks.
 *
 * <p>This custom exception is used to indicate that an operation requiring tasks
 * cannot proceed because the project is empty. It extends {@link Exception} and
 * provides a constructor for passing a descriptive message.</p>
 */
public class EmptyProjectException extends Exception {

    /**
     * Constructs a new {@code EmptyProjectException} with the specified detail message.
     *
     * @param message a descriptive message explaining the reason for the exception;
     */
    public EmptyProjectException(String message) {
        super(message);
    }
}