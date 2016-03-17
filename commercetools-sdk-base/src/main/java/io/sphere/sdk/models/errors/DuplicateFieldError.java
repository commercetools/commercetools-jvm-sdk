package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nullable;

/**
 * A value for a field conflicts with an existing duplicate value.
 *
 *  <p>Example for duplicate customer email</p>
 * {@include.example io.sphere.sdk.models.errors.DuplicateFieldErrorIntegrationTest}
 *
 * @see io.sphere.sdk.client.ErrorResponseException
 */
public final class DuplicateFieldError extends SphereError {
    public static final String CODE = "DuplicateField";
    private final String duplicateValue;
    private final String field;

    @JsonCreator
    private DuplicateFieldError(final String message, final String duplicateValue, final String field) {
        super(CODE, message);
        this.duplicateValue = duplicateValue;
        this.field = field;
    }

    public static DuplicateFieldError of(final String message, final String duplicateValue, final String field) {
        return new DuplicateFieldError(message, duplicateValue, field);
    }

    @Nullable
    public String getField() {
        return field;
    }

    @Nullable
    public String getDuplicateValue() {
        return duplicateValue;
    }
}
