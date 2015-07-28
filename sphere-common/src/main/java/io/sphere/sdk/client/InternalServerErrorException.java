package io.sphere.sdk.client;

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
