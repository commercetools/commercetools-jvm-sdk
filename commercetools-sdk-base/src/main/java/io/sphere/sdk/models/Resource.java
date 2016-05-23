package io.sphere.sdk.models;

import java.time.ZonedDateTime;

/**
 * A default model is a real resource in the platform which can be referenced and always consists of the fields
 * id, version, createdAt, lastModifiedAt.
 * @param <T> the interface which inherits from this interface, example: {@code interface Category extends Resource<Category>}
 *
 */
public interface Resource<T> extends ResourceView<T, T>, Referenceable<T>, Versioned<T> {
    @Override
    String getId();

    @Override
    Long getVersion();

    @Override
    ZonedDateTime getCreatedAt();

    @Override
    ZonedDateTime getLastModifiedAt();

    @Override
    Reference<T> toReference();
}
