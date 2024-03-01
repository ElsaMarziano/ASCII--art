package exceptions;

/**
 * A runtime exception indicating that a command is invalid.
 */
public class InvalidCommandException extends RuntimeException {
    /**
     * Constructor for InvalidCommandException
     *
     * @param message message to print
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
