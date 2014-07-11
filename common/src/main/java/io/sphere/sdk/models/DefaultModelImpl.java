package io.sphere.sdk.models;

import org.joda.time.DateTime;

public abstract class DefaultModelImpl<T> extends Base implements DefaultModel<T> {
    private final String id;
    private final long version;
    private final DateTime createdAt;
    private final DateTime lastModifiedAt;

    public DefaultModelImpl(final String id, final long version, final DateTime createdAt, final DateTime lastModifiedAt) {
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
    public DateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public DateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
