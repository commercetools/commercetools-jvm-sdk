package io.sphere.sdk.exceptions;

/**
 * The SPHERE.IO API is currently not available.
 *
 */
public class ServiceUnavailableException extends ServerErrorException {
    private static final long serialVersionUID = 0L;

    public ServiceUnavailableException() {
        super(503);
    }
}
