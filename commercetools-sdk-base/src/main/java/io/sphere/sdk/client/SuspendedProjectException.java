package io.sphere.sdk.client;

/**
 * when trying to make a call to commercetools with suspended project
 *
 */
public class SuspendedProjectException extends UnauthorizedException{
    private static final long serialVersionUID = 0L;

    public SuspendedProjectException(final Throwable cause) {
        super("Project suspended",cause,400);
    }
}
