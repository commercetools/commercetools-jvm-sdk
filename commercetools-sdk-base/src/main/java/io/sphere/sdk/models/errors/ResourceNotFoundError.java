package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class ResourceNotFoundError extends SphereError {
    public static final String CODE = "ResourceNotFound";

    @JsonCreator
    private ResourceNotFoundError(final String message) {
        super(CODE, message);
    }

    public static ResourceNotFoundError of(final String message) {
        return new ResourceNotFoundError(message);
    }
}
