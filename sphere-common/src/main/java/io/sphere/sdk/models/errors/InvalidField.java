package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereError;

/**
 * @see io.sphere.sdk.client.ErrorResponseException
 */
public class InvalidField extends SphereError {

    public static final String CODE = "InvalidField";

    @JsonCreator
    private InvalidField(final String message) {
        super(CODE, message);
    }

    public static InvalidField of(final String message) {
        return new InvalidField(message);
    }
}
