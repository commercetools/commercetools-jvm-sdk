package io.sphere.sdk.extensions.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class ExtensionUpdateActionsFailedError extends SphereError {
    public static final String CODE = "ExtensionUpdateActionsFailed";

    @JsonCreator
    private ExtensionUpdateActionsFailedError(final String message) {
        super(CODE, message);
    }

    public static ExtensionUpdateActionsFailedError of(final String message) {
        return new ExtensionUpdateActionsFailedError(message);
    }
}
