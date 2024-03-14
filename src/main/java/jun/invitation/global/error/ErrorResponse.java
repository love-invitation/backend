package jun.invitation.global.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private List errors;

    @Builder
    public ErrorResponse(int status, String code, String message, List errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    // 단일 에러
    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(new ArrayList<>())
                .build();
    }

    // 복수 에러
    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {

        List<FieldError> errors = bindingResult.getFieldErrors()
                .stream().map(fieldError -> FieldError.builder()
                        .field(fieldError.getObjectName()+"."+fieldError.getField())
                        .value(fieldError.getRejectedValue())
                        .reason(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private Object value;
        private String reason;

        @Builder
        public FieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}