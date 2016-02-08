package io.sphere.sdk.models;

import java.time.ZonedDateTime;

public abstract class ResourceImpl<T extends Identifiable<T>> extends ResourceViewImpl<T, T> implements Resource<T> {
    protected ResourceImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
        super(id, version, createdAt, lastModifiedAt);
    }
}
