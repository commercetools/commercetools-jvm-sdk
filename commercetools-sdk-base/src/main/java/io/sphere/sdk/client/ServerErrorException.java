package io.sphere.sdk.client;

/**
 * The platform answered with a HTTP response code of &gt;= 500.
 *
 * Simulate this server conditions in test:
 *
 * {@include.example io.sphere.sdk.errors.ServerErrorExceptionDemoTest}
 */
public abstract class ServerErrorException extends SphereServiceException {
    static final long serialVersionUID = 0L;

    protected ServerErrorException(final String message, final Integer statusCode) {
        super(message, statusCode);
    }

    protected ServerErrorException(final Integer statusCode) {
        super(statusCode);
    }
}
