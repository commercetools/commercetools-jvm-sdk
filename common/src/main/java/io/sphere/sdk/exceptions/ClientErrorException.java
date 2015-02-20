package io.sphere.sdk.exceptions;

/**
 * Exceptions for SPHERE.IO http responses with an error code 4xx.
 */
public class ClientErrorException extends SphereServiceException {
    private static final long serialVersionUID = 0L;

    public ClientErrorException(final int statusCode) {
        super(statusCode);
    }

    public ClientErrorException(final String message, final int statusCode) {
        super(message, statusCode);
    }

    public ClientErrorException(final String message, final Throwable cause, final int statusCode) {
        super(message, cause, statusCode);
    }
}