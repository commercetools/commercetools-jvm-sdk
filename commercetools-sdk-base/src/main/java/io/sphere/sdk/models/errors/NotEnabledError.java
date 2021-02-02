package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class NotEnabledError extends SphereError {
    public static final String CODE = "NotEnabled";

    @JsonCreator
    private NotEnabledError(final String message) {
        super(CODE, message);
    }

    public static NotEnabledError of(final String message) {
        return new NotEnabledError(message);
    }
}
