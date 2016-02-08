package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = ErrorResponseImpl.class)
public interface ErrorResponse {
    Integer getStatusCode();

    String getMessage();

    List<? extends SphereError> getErrors();

    default boolean hasErrorCode(final String errorCode) {
        return getErrors().stream().anyMatch(sphereError -> sphereError.getCode().equals(errorCode));
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<ErrorResponse> typeReference() {
        return new TypeReference<ErrorResponse>() {
            @Override
            public String toString() {
                return "TypeReference<ErrorResponse>";
            }
        };
    }

    @JsonIgnore
    static ErrorResponse of(final Integer statusCode, final String message, final List<? extends SphereError> errors) {
        return new ErrorResponseImpl(statusCode, message, errors);
    }
}
