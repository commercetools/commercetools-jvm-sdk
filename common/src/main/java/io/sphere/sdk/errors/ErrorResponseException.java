package io.sphere.sdk.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.List;

public class ErrorResponseException extends BadRequestException implements ErrorResponse {
    private static final long serialVersionUID = 0L;

    private final int statusCode;
    private final String message;
    private final List<SphereError> errors;

    @JsonCreator
    ErrorResponseException(final int statusCode, final String message, final List<SphereError> errors) {
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

    public static TypeReference<ErrorResponseException> typeReference() {
        return new TypeReference<ErrorResponseException>() {
            @Override
            public String toString() {
                return "TypeReference<ErrorResponseException>";
            }
        };
    }
}
