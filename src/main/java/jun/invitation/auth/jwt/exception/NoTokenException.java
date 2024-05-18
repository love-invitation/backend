package jun.invitation.auth.jwt.exception;

public class NoTokenException extends RuntimeException{
    public NoTokenException() {
        super();
    }

    public NoTokenException(String message) {
        super(message);
    }

    public NoTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTokenException(Throwable cause) {
        super(cause);
    }

    protected NoTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
