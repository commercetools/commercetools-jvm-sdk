package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class SemanticError extends SphereError {
    public static final String CODE = "SemanticError";

    @JsonCreator
    private SemanticError(final String message) {
        super(CODE, message);
    }

    public static SemanticError of(final String message) {
        return new SemanticError(message);
    }
}
