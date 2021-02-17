package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class FeatureRemovedError extends SphereError {
    public static final String CODE = "FeatureRemoved";

    @JsonCreator
    private FeatureRemovedError(final String message) {
        super(CODE, message);
    }

    public static FeatureRemovedError of(final String message) {
        return new FeatureRemovedError(message);
    }
}
