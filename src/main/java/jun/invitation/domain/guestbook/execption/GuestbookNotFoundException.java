package jun.invitation.domain.guestbook.execption;

public class GuestbookNotFoundException extends RuntimeException{
    public GuestbookNotFoundException() {
        super();
    }

    public GuestbookNotFoundException(String message) {
        super(message);
    }

    public GuestbookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuestbookNotFoundException(Throwable cause) {
        super(cause);
    }

    protected GuestbookNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
