package io.sphere.sdk.models;

public class VersionedImpl implements Versioned {
    private final String id;
    private final long version;

    public VersionedImpl(final String id, final long version) {
        this.id = id;
        this.version = version;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getVersion() {
        return version;
    }
}
