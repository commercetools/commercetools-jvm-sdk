package io.sphere.sdk.exceptions;

/**
 *
 * <span id="exception-summary">Exception thrown when SPHERE.IO responds<br>with a status code other than HTTP 2xx.</span>
 *
 */
public abstract class SphereServiceException extends SphereException {
    static final long serialVersionUID = 0L;
    private final int statusCode;

    public SphereServiceException(final int statusCode) {
        this.statusCode = statusCode;
    }

    public SphereServiceException(final String message, final int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public SphereServiceException(final Throwable cause, final int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public SphereServiceException(final String message, final Throwable cause, final int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
