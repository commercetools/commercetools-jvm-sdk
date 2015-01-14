package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.DefaultModelViewImpl;

import java.time.Instant;

import static java.lang.String.format;

class CustomObjectImpl<T> extends DefaultModelViewImpl<CustomObject<T>> implements CustomObject<T> {
    private static final String keyContainerRegex = "[-_~.a-zA-Z0-9]+";
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

    @JsonIgnore
    static String validatedKey(final String name, final String keyOrContainer) {
        if (!keyOrContainer.matches(keyContainerRegex)) {
            final String message = format("The %s \"%s\" does not have the correct format. Key and container need to match %s.", name, keyOrContainer, keyContainerRegex);
            throw new IllegalArgumentException(message);
        }
        return keyOrContainer;
    }
}
