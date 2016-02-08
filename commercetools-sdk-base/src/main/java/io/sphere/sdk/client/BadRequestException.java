package io.sphere.sdk.client;

/**
 * HTTP code 400 response from SPHERE.IO.
 *
 */
public class BadRequestException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public BadRequestException(final String message) {
        super(message, 400);
    }
}
