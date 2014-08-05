package io.sphere.sdk.models;

/**
 * Something that is identifiable via an ID.
 * @param <T> The type which has an ID.
 */
public interface Identifiable<T> {

    /**
     * The unique ID of this object.
     * @return ID
     */
    String getId();
}
