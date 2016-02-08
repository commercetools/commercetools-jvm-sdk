package io.sphere.sdk.models;

/**
 * Identifies a resource in the shape of the resource itself, a reference or a key based reference.
 *
 * @param <T> the type of the resource which is referenced to
 */
public interface ResourceIdentifiable<T> {
    ResourceIdentifier<T> toResourceIdentifier();
}
