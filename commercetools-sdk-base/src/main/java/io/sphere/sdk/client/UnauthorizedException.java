package io.sphere.sdk.client;

/**
 * Unauthorized access to the commercetools platform with either invalid client credentials or tokens.
 * Most likely the subclass exceptions {@link InvalidClientCredentialsException} and {@link InvalidTokenException} will be thrown.
 *
 */
public class UnauthorizedException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public UnauthorizedException(final String message, final int statusCode) {
        super(message, statusCode);
    }

    public UnauthorizedException(final String message, final Throwable cause, final int statusCode) {
        super(message, cause, statusCode);
    }
}
