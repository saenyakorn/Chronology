package exception;

/**
 * An exception thrown when the type of object passed is not compatible with the method.
 */
public class TypeNotMatchException extends Exception {
    public TypeNotMatchException() {
        super();
    }

    /**
     * Constructs a TypeNotMatchException along with aspecific error message.
     * @param message the error message to be shown.
     */
    public TypeNotMatchException(String message) {
        super(message);
    }

    public TypeNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeNotMatchException(Throwable cause) {
        super(cause);
    }

    protected TypeNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
