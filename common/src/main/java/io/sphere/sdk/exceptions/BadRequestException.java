package io.sphere.sdk.exceptions;

/**
 * HTTP code 400 response from SPHERE.IO.
 *
 */
public class BadRequestException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public BadRequestException() {
        super(400);
    }
}
