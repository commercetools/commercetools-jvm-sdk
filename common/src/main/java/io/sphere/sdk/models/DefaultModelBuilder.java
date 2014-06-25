package io.sphere.sdk.models;

import org.joda.time.DateTime;

/**
 * A non-fluent builder to reduce the number of type parameters.
 *
 * @param <T> the interface of the type which the builder builds
 * @see io.sphere.sdk.models.DefaultModelFluentBuilder
 */
public abstract class DefaultModelBuilder<T> implements Builder<T> {
    protected String id;
    protected long version = 1;
    protected DateTime createdAt = new DateTime();
    protected DateTime lastModifiedAt = new DateTime();

    public void setId(final String id) {
        this.id = id;
    }

    public void setVersion(final long version) {
        this.version = version;
    }

    public void setCreatedAt(final DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModifiedAt(final DateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
