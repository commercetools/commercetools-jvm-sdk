package io.sphere.sdk.models;

class SimpleVersioned<T> extends Base implements Versioned<T> {
    private final String id;
    private final Long version;

    SimpleVersioned(final String id, final Long version) {
        this.id = id;
        this.version = version;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Long getVersion() {
        return version;
    }
}
