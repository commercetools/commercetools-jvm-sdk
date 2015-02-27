package io.sphere.sdk.errors;

public class InvalidJsonInputError extends SphereError {
    private final String detailedErrorMessage;

    public static final String CODE = "InvalidJsonInput";

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
