package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class GeneralError extends SphereError {
    public static final String CODE = "General";

    @JsonCreator
    private GeneralError(final String message) {
        super(CODE, message);
    }

    public static GeneralError of(final String message) {
        return new GeneralError(message);
    }
}
