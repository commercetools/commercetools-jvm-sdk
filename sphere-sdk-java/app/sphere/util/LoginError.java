package sphere.util;

/** An error that represents a failed user login. */
public class LoginError {
    private final LoginErrorType errorType;
    private final String message;
    private final Throwable exception;

    public LoginError(LoginErrorType errorType, String message, Throwable exception) {
        this.errorType = errorType;
        this.message = message;
        this.exception = exception;
    }

    public LoginError(LoginErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public LoginErrorType getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }
}
