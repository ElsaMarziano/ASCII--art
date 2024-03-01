package ascii_art;

/**
 * Exception thrown to indicate that an invalid action was performed in the ASCII art shell.
 * This exception extends RuntimeException.
 */
public class InvalidActionException extends RuntimeException {
    /**
     * Constructs a new InvalidActionException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *               getMessage() method)
     */
    public InvalidActionException(String message) {
        super(message);
    }
}
