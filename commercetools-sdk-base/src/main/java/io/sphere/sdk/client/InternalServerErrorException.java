package io.sphere.sdk.client;

/**
 * An exception has been thrown on the server side.
 *
 * <p>To simulate for tests this exception see {@link SphereServiceException}.</p>
 */
public class InternalServerErrorException extends ServerErrorException {
    static final long serialVersionUID = 0L;
    private static final Integer STATUS_CODE = 500;

    public InternalServerErrorException(final String message) {
        super(message, STATUS_CODE);
    }

    public InternalServerErrorException() {
        super(STATUS_CODE);
    }
}
