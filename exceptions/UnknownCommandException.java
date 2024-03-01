package exceptions;


/**
 * A runtime exception indicating that the command is unknown.
 */
public class UnknownCommandException extends InvalidCommandException {
    /**
     * Constructor for UnknownCommandException
     *
     * @param message message to print
     */
    public UnknownCommandException(String message) {
        super(message);
    }
}
