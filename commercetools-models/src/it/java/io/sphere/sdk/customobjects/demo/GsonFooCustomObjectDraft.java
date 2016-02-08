package io.sphere.sdk.customobjects.demo;

public class GsonFooCustomObjectDraft {
    private final GsonFoo value;
    private final String container;
    private final String key;

    public GsonFooCustomObjectDraft(final String container, final String key, final GsonFoo value) {
        this.container = container;
        this.value = value;
        this.key = key;
    }
}
