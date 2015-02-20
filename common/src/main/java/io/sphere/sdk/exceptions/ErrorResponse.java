package io.sphere.sdk.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = ErrorResponseImpl.class)
public interface ErrorResponse {
    int getStatusCode();

    String getMessage();

    List<SphereError> getErrors();

    default boolean hasErrorCode(final String errorCode) {
        return getErrors().stream().anyMatch(sphereError -> sphereError.getCode().equals(errorCode));
    }

    static TypeReference<ErrorResponse> typeReference() {
        return new TypeReference<ErrorResponse>() {
            @Override
            public String toString() {
                return "TypeReference<ErrorResponse>";
            }
        };
    }

    @JsonIgnore
    static ErrorResponse of(final int statusCode, final String message, final List<SphereError> errors) {
        return new ErrorResponseImpl(statusCode, message, errors);
    }
}
