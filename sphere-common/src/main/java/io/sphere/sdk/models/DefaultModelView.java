package io.sphere.sdk.models;

import java.time.ZonedDateTime;

/**
 * A default model view is a view for a resource in SPHERE.IO always consists of the fields
 * id, version, createdAt, lastModifiedAt.
 * @param <T> the interface which inherits from this interface
 *
 */
public interface DefaultModelView<T> extends Timestamped, Versioned<T> {
    @Override
    String getId();

    @Override
    Long getVersion();

    @Override
    ZonedDateTime getCreatedAt();

    @Override
    ZonedDateTime getLastModifiedAt();
}
