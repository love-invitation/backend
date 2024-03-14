package jun.invitation.domain.invitation.exception;

public class InvitationAccessDeniedException extends RuntimeException{
    public InvitationAccessDeniedException() {
        super();
    }

    public InvitationAccessDeniedException(String message) {
        super(message);
    }

    public InvitationAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvitationAccessDeniedException(Throwable cause) {
        super(cause);
    }

    protected InvitationAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
