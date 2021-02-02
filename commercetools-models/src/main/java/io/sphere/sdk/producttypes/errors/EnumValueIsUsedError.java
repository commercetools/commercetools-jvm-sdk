package io.sphere.sdk.producttypes.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class EnumValueIsUsedError extends SphereError {
    public static final String CODE = "EnumValueIsUsed";

    @JsonCreator
    private EnumValueIsUsedError(final String message) {
        super(CODE, message);
    }

    public static EnumValueIsUsedError of(final String message) {
        return new EnumValueIsUsedError(message);
    }
}
