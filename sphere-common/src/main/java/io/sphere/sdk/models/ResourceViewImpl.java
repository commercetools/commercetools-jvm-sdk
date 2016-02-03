package io.sphere.sdk.models;

import java.time.ZonedDateTime;

public abstract class ResourceViewImpl<T, O> extends Base implements ResourceView<T, O> {
    private final String id;
    private final Long version;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime lastModifiedAt;

    public ResourceViewImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
        this.id = id;
        this.version = version;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public ZonedDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
