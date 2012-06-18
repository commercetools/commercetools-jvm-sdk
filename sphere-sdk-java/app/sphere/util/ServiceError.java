package sphere.util;

/** An error from some of Sphere's HTTP services. */
public class ServiceError {
    private final ServiceErrorType errorType;
    private final String message;
    private final Throwable exception;

    public ServiceError(ServiceErrorType errorType, String message, Throwable exception) {
        this.errorType = errorType;
        this.message = message;
        this.exception = exception;
    }

    public ServiceError(ServiceErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public ServiceErrorType getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }
}
