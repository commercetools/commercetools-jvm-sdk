package io.sphere.sdk.models;

import java.time.Instant;

public abstract class DefaultModelImpl<T extends Identifiable<T>> extends DefaultModelViewImpl<T> implements DefaultModel<T> {
    protected DefaultModelImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt) {
        super(id, version, createdAt, lastModifiedAt);
    }
}
