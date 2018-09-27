package io.sphere.sdk.client;

/**
 * Exception raised in case the access token is not valid for the used commercetools platform project.
 */
public class InvalidTokenException extends UnauthorizedException {
    private static final long serialVersionUID = 0L;

    public InvalidTokenException() {
        super("Invalid token for request.",401);
    }
}
