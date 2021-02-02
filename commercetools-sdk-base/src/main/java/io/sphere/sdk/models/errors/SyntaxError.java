package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class SyntaxError extends SphereError {
    public static final String CODE = "SyntaxError";

    @JsonCreator
    private SyntaxError(final String message) {
        super(CODE, message);
    }

    public static SyntaxError of(final String message) {
        return new SyntaxError(message);
    }
}
