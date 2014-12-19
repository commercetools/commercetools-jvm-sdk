package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.core.type.TypeReference;

public class CustomObjectDraft<T> extends CustomObjectKey {
    private final T value;
    private final TypeReference<CustomObject<T>> typeReference;

    private CustomObjectDraft(final String container, final String key, final T value, final TypeReference<CustomObject<T>> typeReference) {
        super(container, key);
        this.value = value;
        this.typeReference = typeReference;
    }

    public T getValue() {
        return value;
    }

    public TypeReference<CustomObject<T>> typeReference() {
        return typeReference;
    }

    public static <T> CustomObjectDraft<T> of(final String container, final String key, final T value, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectDraft<>(container, key, value, typeReference);
    }
}
