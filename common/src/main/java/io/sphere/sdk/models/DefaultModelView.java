package io.sphere.sdk.models;

/**
 * A default model view is a view for a resource in SPHERE.IO always consists of the fields
 * id, version, createdAt, lastModifiedAt.
 * @param <T> the interface which inherits from this interface
 *
 */
public interface DefaultModelView<T> extends Timestamped, Versioned<T> {
}
