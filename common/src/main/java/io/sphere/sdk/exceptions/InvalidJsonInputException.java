package io.sphere.sdk.exceptions;

/**
 * JSON send to SPHERE.IO is invalid. Check for missing parenthesis, brackets and quotes.
 *
 * {@include.example io.sphere.sdk.exceptions.SphereExceptionTest#invalidJsonInHttpRequestIntent()}
 *
 */
public class InvalidJsonInputException extends SphereErrorResponseBadRequestException {
    private static final long serialVersionUID = 0L;

    public InvalidJsonInputException(final ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
