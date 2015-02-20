package io.sphere.sdk.exceptions;

/**
 * SPHERE.IO answered with a HTTP response code of &gt;= 500.
 */
public abstract class ServerErrorException extends SphereServiceException {
    static final long serialVersionUID = 0L;

    protected ServerErrorException(final int statusCode) {
        super(statusCode);
    }
}
