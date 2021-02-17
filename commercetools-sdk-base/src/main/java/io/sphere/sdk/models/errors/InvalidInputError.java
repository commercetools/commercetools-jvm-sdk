package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class InvalidInputError extends SphereError {
    public static final String CODE = "InvalidInput";

    @JsonCreator
    private InvalidInputError(final String message) {
        super(CODE, message);
    }

    public static InvalidInputError of(final String message) {
        return new InvalidInputError(message);
    }
}
