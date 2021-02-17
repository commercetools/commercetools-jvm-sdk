package io.sphere.sdk.producttypes.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class EnumValuesMustMatchError extends SphereError {
    public static final String CODE = "EnumValuesMustMatch";

    @JsonCreator
    private EnumValuesMustMatchError(final String message) {
        super(CODE, message);
    }

    public static EnumValuesMustMatchError of(final String message) {
        return new EnumValuesMustMatchError(message);
    }
}
