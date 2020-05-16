package exception;

public class TypeNotMatchException extends Exception {
    public TypeNotMatchException() {
        super();
    }

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
