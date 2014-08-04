package io.sphere.sdk.models;

class SimpleVersioned extends Base implements Versioned {
    private final String id;
    private final long version;

    SimpleVersioned(final String id, final long version) {
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
