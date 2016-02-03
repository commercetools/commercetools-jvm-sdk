package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @see io.sphere.sdk.client.ErrorResponseException
 */
public final class RequiredField extends SphereError {

    public static final String CODE = "RequiredField";
    private final String field;

    @JsonCreator
    private RequiredField(final String message, final String field) {
        super(CODE, message);
        this.field = field;
    }

    public static RequiredField of(final String message, final String field) {
        return new RequiredField(message, field);
    }

    public String getField() {
        return field;
    }
}
