package io.sphere.sdk.models;

public interface Versioned {
    String getId();
    long getVersion();

    static Versioned of(final String id, final long version) {
        return new SimpleVersioned(id, version);
    }
}
