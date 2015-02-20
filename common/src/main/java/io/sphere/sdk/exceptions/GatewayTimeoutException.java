package io.sphere.sdk.exceptions;

/**
 * This error might occur on long running processes
 * such as deletion of resources with connections to other resources.
 */
public class GatewayTimeoutException extends ServerErrorException {
    private static final long serialVersionUID = 0L;

    public GatewayTimeoutException() {
        super(504);
    }
}
