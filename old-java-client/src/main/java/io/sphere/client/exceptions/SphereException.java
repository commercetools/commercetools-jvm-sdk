package io.sphere.client.exceptions;

import io.sphere.client.SphereClientException;

/** Exception thrown when a Sphere web service responds with a status code other than HTTP 2xx.
 *
 *  The subclasses of this class are specific exceptions in the {@code io.sphere.client.exceptions} package,
 *  and the generic {@link SphereBackendException}. */
public class SphereException extends SphereClientException {
    protected SphereException() {}

    public SphereException(String message) {
        super(message);
    }

    public SphereException(String message, Throwable cause) {
        super(message + ": " + cause.getMessage(), cause);
    }
    
    public SphereException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
