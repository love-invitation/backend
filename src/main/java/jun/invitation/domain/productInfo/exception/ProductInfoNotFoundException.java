package jun.invitation.domain.productInfo.exception;

public class ProductInfoNotFoundException extends RuntimeException{
    public ProductInfoNotFoundException() {
        super();
    }

    public ProductInfoNotFoundException(String message) {
        super(message);
    }

    public ProductInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductInfoNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProductInfoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
