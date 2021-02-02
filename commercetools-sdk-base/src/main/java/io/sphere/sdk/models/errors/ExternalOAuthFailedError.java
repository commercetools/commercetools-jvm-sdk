package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class ExternalOAuthFailedError extends SphereError {
    public static final String CODE = "ExternalOAuthFailed";

    @JsonCreator
    private ExternalOAuthFailedError(final String message) {
        super(CODE, message);
    }

    public static ExternalOAuthFailedError of(final String message) {
        return new ExternalOAuthFailedError(message);
    }
}
