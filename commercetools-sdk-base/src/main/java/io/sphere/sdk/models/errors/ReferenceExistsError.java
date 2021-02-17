package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class ReferenceExistsError extends SphereError {
    public static final String CODE = "ReferenceExists";

    @JsonCreator
    private ReferenceExistsError(final String message) {
        super(CODE, message);
    }

    public static ReferenceExistsError of(final String message) {
        return new ReferenceExistsError(message);
    }
}
