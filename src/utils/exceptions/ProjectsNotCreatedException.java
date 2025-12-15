
package utils.exceptions;

/**
 * Exception thrown when an operation requiring existing projects fails because no projects have been created.
 *
 * <p>This custom exception is typically used in scenarios where a method expects at least one project
 * to exist in the system but finds none. It extends {@link Exception} and allows passing a descriptive
 * message to clarify the reason for the failure.</p>
 *
 */
public class ProjectsNotCreatedException extends Exception {

    /**
     * Constructs a new {@code ProjectsNotCreatedException} with the specified detail message.
     *
     * @param message a descriptive message explaining why the operation
     */
    public ProjectsNotCreatedException(String message) {
        super(message);
    }
}