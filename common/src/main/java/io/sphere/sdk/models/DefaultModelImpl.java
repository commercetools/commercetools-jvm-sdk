package io.sphere.sdk.models;


import java.time.Instant;

public abstract class DefaultModelImpl<T> extends Base implements DefaultModel<T> {
    private final String id;
    private final long version;
    private final Instant createdAt;
    private final Instant lastModifiedAt;

    public DefaultModelImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt) {
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
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }
}
