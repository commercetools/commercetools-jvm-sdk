package io.sphere.sdk.models;

import java.time.ZonedDateTime;

/**
 * A default model is a real resource in SPHERE.IO which can be referenced and always consists of the fields
 * id, version, createdAt, lastModifiedAt.
 * @param <T> the interface which inherits from this interface, example: {@code interface Category extends DefaultModel<Category>}
 *
 */
public interface DefaultModel<T> extends DefaultModelView<T>, Referenceable<T> {
    @Override
    String getId();

    @Override
    long getVersion();

    @Override
    ZonedDateTime getCreatedAt();

    @Override
    ZonedDateTime getLastModifiedAt();

    @Override
    Reference<T> toReference();
}
