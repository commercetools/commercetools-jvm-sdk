package io.sphere.sdk.client;

/**
 * Unauthorized access to the commercetools platform with either invalid client credentials or tokens.
 * Most likely the subclass exceptions {@link InvalidClientCredentialsException} and {@link InvalidTokenException} will be thrown.
 *
 */
public class UnauthorizedException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public UnauthorizedException(final String message) {
        super(message, 401);
    }

    public UnauthorizedException(final String message, final Throwable cause) {
        super(message, cause, 401);
    }
}
