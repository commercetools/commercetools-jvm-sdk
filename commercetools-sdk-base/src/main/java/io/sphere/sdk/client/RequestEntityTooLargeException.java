package io.sphere.sdk.client;

/**
 * HTTP code 413 response from the platform.
 *
 * Probable error cause: Predicate for a query is too long. Try to split the request into multiple ones or use the in operator.
 *
 */
public class RequestEntityTooLargeException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public RequestEntityTooLargeException() {
        super(413);
    }
}
