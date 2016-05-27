package io.sphere.sdk.client;

/**
 * Exceptions for commercetools platform http responses with an error code 4xx.
 */
public class ClientErrorException extends SphereServiceException {
    private static final long serialVersionUID = 0L;

    public ClientErrorException(final Integer statusCode) {
        super(statusCode);
    }

    public ClientErrorException(final String message, final Integer statusCode) {
        super(message, statusCode);
    }

    public ClientErrorException(final String message, final Throwable cause, final Integer statusCode) {
        super(message, cause, statusCode);
    }
}