package io.sphere.sdk.client;

/**
 * The commercetools platform received the request but refuses to process it, typically to insufficient rights.
 *
 * For example it happens if an access token has been received to view products but a request to the customers endpoint is requested.
 *
 * {@include.example io.sphere.sdk.errors.SphereExceptionIntegrationTest#permissionsExceeded()}
 */
public class ForbiddenException extends ClientErrorException {
    private static final int STATUS_CODE = 403;

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause, STATUS_CODE);
    }

    public ForbiddenException(final String message) {
        super(message, STATUS_CODE);
    }

    public ForbiddenException() {
        super(STATUS_CODE);
    }
}
