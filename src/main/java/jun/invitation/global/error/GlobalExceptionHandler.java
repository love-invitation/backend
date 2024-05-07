package jun.invitation.global.error;

import jun.invitation.domain.guestbook.execption.GuestbookNotFoundException;
import jun.invitation.domain.invitation.exception.InvitationAccessDeniedException;
import jun.invitation.domain.invitation.exception.InvitationNotFoundException;
import jun.invitation.domain.user.exception.UserNotFoundException;
import jun.invitation.global.exception.PasswordMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* REQUEST BODY */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_REQUEST, e.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /* GLOBAL ERROR */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlerPasswordMismatchException(PasswordMismatchException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.MISMATCH_PASSWORD);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /* USER ERROR */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.USER_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /* INVITATION ERROR */
    @ExceptionHandler(InvitationAccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handlerInvitationAccessDeniedException(InvitationAccessDeniedException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ACCESS_DENIED);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerInvitationNotFoundException(InvitationNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVITATION_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /* GUESTBOOK ERROR */
    @ExceptionHandler(GuestbookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerGuestbookNotFoundException(GuestbookNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.GUESTBOOK_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
