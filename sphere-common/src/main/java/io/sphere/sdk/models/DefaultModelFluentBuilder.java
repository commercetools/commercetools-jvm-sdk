package io.sphere.sdk.models;


import java.time.Instant;

/**
 * A builder base class.
 * @param <B> the type of the builder subclass
 * @param <I> the interface of the type which the builder builds
 */
public abstract class DefaultModelFluentBuilder<B, I> extends DefaultModelBuilder<I> {


    public B id(final String id) {
        setId(id);
        return getThis();
    }

    public B version(final long version) {
        setVersion(version);
        return getThis();
    }

    public B createdAt(final Instant createdAt) {
        setCreatedAt(createdAt);
        return getThis();
    }

    public B lastModifiedAt(final Instant lastModifiedAt) {
       setLastModifiedAt(lastModifiedAt);
       return getThis();
    }

    protected abstract B getThis();
}
