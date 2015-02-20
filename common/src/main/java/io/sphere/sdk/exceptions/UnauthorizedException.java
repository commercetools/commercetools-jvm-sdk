package io.sphere.sdk.exceptions;

public class UnauthorizedException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public UnauthorizedException() {
        super(401);
    }

    public UnauthorizedException(final String message) {
        super(message, 401);
    }

    public UnauthorizedException(final String message, final Throwable cause) {
        super(message, cause, 401);
    }
}
