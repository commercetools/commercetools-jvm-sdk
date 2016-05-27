package io.sphere.sdk.models;

import java.time.ZonedDateTime;

/**
 * A view for a resource always containing the fields
 * id, version, createdAt, lastModifiedAt.
 * @param <T> the interface which inherits from this interface
 * @param <O> the interface which belongs to {@link Versioned}
 *
 */
public interface ResourceView<T, O> extends Timestamped, Versioned<O> {
    @Override
    String getId();

    @Override
    Long getVersion();

    @Override
    ZonedDateTime getCreatedAt();

    @Override
    ZonedDateTime getLastModifiedAt();
}
