package io.sphere.sdk.client;

/**
 * On the server occurred a problem, try again later.
 */
public class BadGatewayException extends ServerErrorException {
    private static final long serialVersionUID = 0L;
    private static final Integer STATUS_CODE = 502;

    public BadGatewayException(final String message) {
        super(message, STATUS_CODE);
    }

    public BadGatewayException() {
        super(STATUS_CODE);
    }
}
