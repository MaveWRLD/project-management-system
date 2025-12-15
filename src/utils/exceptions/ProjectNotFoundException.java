
package utils.exceptions;

/**
 * Exception thrown when a Project with the specified ID cannot be found.
 *
 * <p>This custom exception is used to indicate that an operation requiring a valid project
 * reference failed because the project does not exist in the system. It extends {@link Exception}
 * and provides a constructor that includes the missing project's ID in the error message for clarity.</p>
 */
public class ProjectNotFoundException extends Exception {

    /**
     * Constructs a new {@code ProjectNotFoundException} with a detail message that includes
     * the missing project's ID.
     *
     * @param projectID the unique identifier of the project that was not found; must not
     */
    public ProjectNotFoundException(String projectID) {
        super("Project with ID " + projectID + " was not found");
    }
}
