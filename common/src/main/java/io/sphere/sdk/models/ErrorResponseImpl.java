package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Collections;
import java.util.List;


final class ErrorResponseImpl extends Base implements ErrorResponse {
    private final int statusCode;
    private final String message;
    private final List<SphereError> errors;

    @JsonCreator
    ErrorResponseImpl(final int statusCode, final String message, final List<SphereError> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors == null ? Collections.<SphereError>emptyList() : errors;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * The message of the first error, for convenience.
     * @return the first error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SphereError> getErrors() {
        return errors;
    }
}
