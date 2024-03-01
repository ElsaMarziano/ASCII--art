package exceptions;

/**
 * A runtime exception indicating that the command action is invalid.
 */
public class InvalidActionException extends InvalidCommandException {
    /**
     * Constructor for InvalidAction
     *
     * @param message message to print
     */
    public InvalidActionException(String message) {
        super(message);
    }
}
