package io.sphere.sdk.extensions.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class ExtensionBadResponseError extends SphereError {
    public static final String CODE = "ExtensionBadResponse";

    @JsonCreator
    private ExtensionBadResponseError(final String message) {
        super(CODE, message);
    }

    public static ExtensionBadResponseError of(final String message) {
        return new ExtensionBadResponseError(message);
    }
}
