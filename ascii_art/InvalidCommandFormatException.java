package ascii_art;

/**
 * A runtime exception indicating that the command format is invalid.
 */
public class InvalidCommandFormatException extends InvalidCommandException {
    public InvalidCommandFormatException(String message) {
        super(message);
    }
}
