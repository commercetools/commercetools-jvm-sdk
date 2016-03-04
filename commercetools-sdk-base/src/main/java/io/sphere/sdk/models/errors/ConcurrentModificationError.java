package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nullable;

/**
 * Version conflict error.
 *
 * @see io.sphere.sdk.client.ConcurrentModificationException
 */
public final class ConcurrentModificationError extends SphereError {
    @Nullable
    private final Long currentVersion;

    public static final String CODE = "ConcurrentModification";

    @JsonCreator
    private ConcurrentModificationError(final String message, @Nullable final Long currentVersion) {
        super(CODE, message);
        this.currentVersion = currentVersion;
    }

    public static ConcurrentModificationError of(final String message, final Long currentVersion) {
        return new ConcurrentModificationError(message, currentVersion);
    }

    @Nullable
    public Long getCurrentVersion() {
        return currentVersion;
    }
}
