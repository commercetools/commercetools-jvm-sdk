package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @see io.sphere.sdk.client.ErrorResponseException
 */
public final class InvalidField extends SphereError {

    public static final String CODE = "InvalidField";

    @JsonCreator
    private InvalidField(final String message) {
        super(CODE, message);
    }

    public static InvalidField of(final String message) {
        return new InvalidField(message);
    }
}
