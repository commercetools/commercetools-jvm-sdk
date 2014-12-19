package io.sphere.sdk.customobjects;

public abstract class CustomObjectKey {
    private final String container;
    private final String key;

    protected CustomObjectKey(final String container, final String key) {
        this.container = container;
        this.key = key;
    }

    public String getContainer() {
        return container;
    }

    public String getKey() {
        return key;
    }
}
