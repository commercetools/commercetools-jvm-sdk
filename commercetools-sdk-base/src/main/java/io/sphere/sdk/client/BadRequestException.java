package io.sphere.sdk.client;

/**
 * HTTP code 400 response from the platform.
 *
 * <p>Typically the subclass {@link ErrorResponseException} is thrown.</p>
 *
 */
public class BadRequestException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public BadRequestException(final String message) {
        super(message, 400);
    }
}
