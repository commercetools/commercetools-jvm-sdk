package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelViewImpl;

import java.time.Instant;

class CustomObjectImpl<T> extends DefaultModelViewImpl<CustomObject<T>> implements CustomObject<T> {
    private final String container;
    private final String key;
    private final T value;


    @JsonCreator
    CustomObjectImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final String container, final String key, final T value) {
        super(id, version, createdAt, lastModifiedAt);
        this.container = container;
        this.key = key;
        this.value = value;
    }

    public String getContainer() {
        return container;
    }

    public String getKey() {
        return key;
    }

    @Override
    public T getValue() {
        return value;
    }
}
