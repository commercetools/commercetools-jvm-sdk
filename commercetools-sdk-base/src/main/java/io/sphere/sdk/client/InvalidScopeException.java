package io.sphere.sdk.client;

/**
 * when trying to make a commercetoold eith invalid scope
 *
 */
public class InvalidScopeException extends UnauthorizedException{
    private static final long serialVersionUID = 0L;

    public InvalidScopeException(final Throwable cause) {
        super("Invalid scope error",cause,400);
    }
}
