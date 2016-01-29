package io.sphere.sdk.models;

/**
 * Represents an object itself or a {@link io.sphere.sdk.models.Reference} to it.
 * It is not necessarily the case that the reference is filled.
 *
 * @param <T> the type of the referenced object.
 */
public interface Referenceable<T> extends ResourceIdentifiable<T> {
    /**
     * Creates a reference to this resource, the reference may not be filled.
     *
     * @return reference
     */
    Reference<T> toReference();

    @Override
    default ResourceIdentifier<T> toResourceIdentifier() {
        return toReference();
    }

    default boolean hasSameIdAs(final Referenceable<T> other) {
        return toReference().getId().equals(other.toReference().getId());
    }
}
