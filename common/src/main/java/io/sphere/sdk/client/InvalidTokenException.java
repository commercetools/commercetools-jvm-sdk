package io.sphere.sdk.client;

public class InvalidTokenException extends UnauthorizedException {
    private static final long serialVersionUID = 0L;

    public InvalidTokenException() {
        super("Invalid token for request.");
    }
}
