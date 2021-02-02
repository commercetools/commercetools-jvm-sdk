package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class PendingOperationError extends SphereError {
    public static final String CODE = "PendingOperation";

    @JsonCreator
    private PendingOperationError(final String message) {
        super(CODE, message);
    }

    public static PendingOperationError of(final String message) {
        return new PendingOperationError(message);
    }
}
