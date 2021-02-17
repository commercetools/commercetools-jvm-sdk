package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class InvalidOperationError extends SphereError {
    public static final String CODE = "InvalidOperation";

    @JsonCreator
    private InvalidOperationError(final String message) {
        super(CODE, message);
    }

    public static InvalidOperationError of(final String message) {
        return new InvalidOperationError(message);
    }
}
