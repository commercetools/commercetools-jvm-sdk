package io.sphere.sdk.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

public abstract class DefaultModelImpl extends Base implements DefaultModel {
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

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
