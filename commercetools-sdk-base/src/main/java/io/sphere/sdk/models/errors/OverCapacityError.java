package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class OverCapacityError extends SphereError {
    public static final String CODE = "OverCapacity";

    @JsonCreator
    private OverCapacityError(final String message) {
        super(CODE, message);
    }

    public static OverCapacityError of(final String message) {
        return new OverCapacityError(message);
    }
}
