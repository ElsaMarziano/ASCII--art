package exceptions;

/**
 * A runtime exception indicating that the command action is invalid.
 */
public class InvalidActionException extends InvalidCommandException {
    public InvalidActionException(String message) {
        super(message);
    }
}
