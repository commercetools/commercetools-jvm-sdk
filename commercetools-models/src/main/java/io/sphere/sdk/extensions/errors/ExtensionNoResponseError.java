package io.sphere.sdk.extensions.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class ExtensionNoResponseError extends SphereError {
    public static final String CODE = "ExtensionNoResponse";

    @JsonCreator
    private ExtensionNoResponseError(final String message) {
        super(CODE, message);
    }

    public static ExtensionNoResponseError of(final String message) {
        return new ExtensionNoResponseError(message);
    }
}
