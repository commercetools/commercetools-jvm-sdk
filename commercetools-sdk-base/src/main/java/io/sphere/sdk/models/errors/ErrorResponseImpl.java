package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.util.Collections;
import java.util.List;


final class ErrorResponseImpl extends Base implements ErrorResponse {
    private final Integer statusCode;
    private final String message;
    private final List<? extends SphereError> errors;

    @JsonCreator
    ErrorResponseImpl(final Integer statusCode, final String message, final List<? extends SphereError> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors == null ? Collections.<SphereError>emptyList() : errors;
    }

    @Override
    public Integer getStatusCode() {
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
    public List<? extends SphereError> getErrors() {
        return errors;
    }
}
