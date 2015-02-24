package io.sphere.sdk.errors;

public class BadGatewayException extends ServerErrorException {
    private static final long serialVersionUID = 0L;

    public BadGatewayException() {
        super(502);
    }
}
