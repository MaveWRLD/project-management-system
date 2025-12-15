
package utils.exceptions;

/**
 * Exception thrown to indicate that an invalid input was provided to a method or operation.
 *
 * <p>This custom exception is typically used for validation failures, such as null values,
 * empty strings, or inputs that do not meet required constraints. It extends {@link Exception}
 * and allows passing a descriptive message to explain the cause of the error.</p>
 *
 */
public class InvalidInputException extends Exception {

    /**
     * Constructs a new {@code InvalidInputException} with the specified detail message.
     *
     * @param message a descriptive message explaining why the input is invalid; must not be {@code null}.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}