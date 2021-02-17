package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class InternalConstraintViolatedError extends SphereError {
    public static final String CODE = "InternalConstraintViolated";

    @JsonCreator
    private InternalConstraintViolatedError(final String message) {
        super(CODE, message);
    }

    public static InternalConstraintViolatedError of(final String message) {
        return new InternalConstraintViolatedError(message);
    }
}
