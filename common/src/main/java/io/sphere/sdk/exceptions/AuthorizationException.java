package io.sphere.sdk.exceptions;


public class AuthorizationException extends SphereServiceException {
    private static final long serialVersionUID = 0L;
    public static final int STATUS_CODE = 401;

    public AuthorizationException(String message) {
        super(message, STATUS_CODE);
    }

    public AuthorizationException(Throwable cause) {
        super(cause, STATUS_CODE);
    }

    public AuthorizationException(final String message, final Throwable cause) {
        super(message, cause, STATUS_CODE);
    }
}
