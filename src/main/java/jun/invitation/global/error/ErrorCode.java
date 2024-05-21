package jun.invitation.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* BAD REQUEST ERROR CODE*/
    BAD_REQUEST (HttpStatus.BAD_REQUEST.value(), "E40001", "wrong input"),

    /* UNAUTHORIZED ERROR CODE*/
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED.value(), "E40101", "unauthorized access"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40102", "token expired. refresh it."),
    NO_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40103", "No token. check your cookie."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40104", "wrong token. check your token."),
    MISMATCH_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "E40105", "mismatch password. check password."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40106", "invalid refresh token. login again."),

    /* FORBIDDEN ERROR CODE*/
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "E40301", "you don't have permission to access."),

    /* NOT FOUND ERROR CODE*/
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E40401", "can't find user. please logout and login again."),
    INVITATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E40402", "can't find the invitation."),
    GUESTBOOK_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E40403", "can't find the guestbook."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E40404", "can't find the order."),
    PRODUCTINFO_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E40405", "can't find the productInfo.");

    private final int status;
    private final String code;
    private final String message;
}
