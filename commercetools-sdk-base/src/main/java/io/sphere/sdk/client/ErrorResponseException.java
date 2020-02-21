package io.sphere.sdk.client;

import io.sphere.sdk.models.errors.ErrorResponse;
import io.sphere.sdk.models.errors.SphereError;

import java.util.Collections;
import java.util.List;

/**
 Typical exception for bad requests containing error information.

 <p>A <a href="https://docs.commercetools.com/http-api-errors.html#400-bad-request" target="_blank">list of error codes</a> can be found on the commercetools website.</p>

 */
public class ErrorResponseException extends BadRequestException implements ErrorResponse {
    private static final long serialVersionUID = 0L;

    private final Integer statusCode;
    private final List<? extends SphereError> errors;

    public ErrorResponseException(final ErrorResponse errorResponse) {
        this(errorResponse.getStatusCode(), errorResponse.getMessage(), errorResponse.getErrors());
    }

    ErrorResponseException(final Integer statusCode, final String message, final List<? extends SphereError> errors) {
        super(message);
        this.statusCode = statusCode;
        this.errors = errors == null ? Collections.<SphereError>emptyList() : errors;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public List<? extends SphereError> getErrors() {
        return errors;
    }
}
