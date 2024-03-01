package exceptions;

/**
 * A runtime exception indicating that the command format is invalid.
 */
public class InvalidCommandFormatException extends InvalidCommandException {
    /**
     * Constructor for InvalidCommandFormatException
     *
     * @param message message to print
     */
    public InvalidCommandFormatException(String message) {
        super(message);
    }
}
