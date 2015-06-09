package io.sphere.sdk.models;

import java.time.ZonedDateTime;

public class DefaultModelViewImpl<T> extends Base implements DefaultModelView<T> {
    private final String id;
    private final long version;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime lastModifiedAt;

    public DefaultModelViewImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt) {
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
    public long getVersion() {
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
