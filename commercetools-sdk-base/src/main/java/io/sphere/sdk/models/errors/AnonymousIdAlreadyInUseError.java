package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class AnonymousIdAlreadyInUseError extends SphereError {
    public static final String CODE = "AnonymousIdAlreadyInUse";

    @JsonCreator
    private AnonymousIdAlreadyInUseError(final String message) {
        super(CODE, message);
    }

    public static AnonymousIdAlreadyInUseError of(final String message) {
        return new AnonymousIdAlreadyInUseError(message);
    }
}
