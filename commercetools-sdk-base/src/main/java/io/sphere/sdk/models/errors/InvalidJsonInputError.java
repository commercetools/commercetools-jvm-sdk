package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Invalid JSON was sent to the platform.
 */
public final class InvalidJsonInputError extends SphereError {
    private final String detailedErrorMessage;

    public static final String CODE = "InvalidJsonInput";

    @JsonCreator
    private InvalidJsonInputError(final String message, final String detailedErrorMessage) {
        super(CODE, message);
        this.detailedErrorMessage = detailedErrorMessage;
    }

    public static InvalidJsonInputError of(final String message, final String detailedErrorMessage) {
        return new InvalidJsonInputError(message, detailedErrorMessage);
    }

    public String getDetailedErrorMessage() {
        return detailedErrorMessage;
    }
}
