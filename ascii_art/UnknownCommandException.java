package ascii_art;


/**
 * A runtime exception indicating that the command is unknown.
 */
public class UnknownCommandException extends InvalidCommandException {
    public UnknownCommandException(String message) {
        super(message);
    }
}
