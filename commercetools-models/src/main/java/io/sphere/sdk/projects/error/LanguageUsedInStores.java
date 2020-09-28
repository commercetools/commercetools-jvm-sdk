package io.sphere.sdk.projects.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

public final class LanguageUsedInStores extends SphereError {

    public static final String CODE = "LanguageUsedInStores";

    @JsonCreator
    private LanguageUsedInStores(final String message) {
        super(CODE, message);
    }

    public static LanguageUsedInStores of(final String message) {
        return new LanguageUsedInStores(message);
    }
}
