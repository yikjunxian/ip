package exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends GumboException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }
}
