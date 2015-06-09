package io.sphere.sdk.models;

import java.time.ZonedDateTime;

public abstract class DefaultModelImpl<T extends Identifiable<T>> extends DefaultModelViewImpl<T> implements DefaultModel<T> {
    protected DefaultModelImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
        super(id, version, createdAt, lastModifiedAt);
    }
}
