package ascii_art;

/**
 * A runtime exception indicating that a command is invalid.
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
