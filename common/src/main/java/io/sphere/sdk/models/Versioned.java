package io.sphere.sdk.models;

/**
 * Something that has an ID and a version.
 *
 * @param <T> The type which has an ID and version.
 */
public interface Versioned<T> extends Identifiable<T> {
    long getVersion();

    static <T> Versioned<T> of(final String id, final long version) {
        return new SimpleVersioned<>(id, version);
    }
}
