package io.sphere.sdk.models;

public final class VersionedFactory {
    private VersionedFactory() {
        //utility class
    }

    public static Versioned versionedOf(final String id, final long version) {
        return new Versioned() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public long getVersion() {
                return version;
            }
        };
    }
}
