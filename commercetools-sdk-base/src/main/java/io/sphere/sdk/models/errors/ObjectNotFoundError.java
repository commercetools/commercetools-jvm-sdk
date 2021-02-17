package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class ObjectNotFoundError extends SphereError {
    public static final String CODE = "ObjectNotFound";

    @JsonCreator
    private ObjectNotFoundError(final String message) {
        super(CODE, message);
    }

    public static ObjectNotFoundError of(final String message) {
        return new ObjectNotFoundError(message);
    }
}
