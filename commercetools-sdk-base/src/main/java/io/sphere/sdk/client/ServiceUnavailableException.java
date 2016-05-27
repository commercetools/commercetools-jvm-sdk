package io.sphere.sdk.client;

/**
 * The platform is currently not available.
 *
 * <p>To simulate for tests this exception see {@link SphereServiceException}.</p>
 *
 */
public class ServiceUnavailableException extends ServerErrorException {
    private static final long serialVersionUID = 0L;
    private static final Integer STATUS_CODE = 503;

    public ServiceUnavailableException(final String message) {
        super(message, STATUS_CODE);
    }

    public ServiceUnavailableException() {
        super(STATUS_CODE);
    }
}
