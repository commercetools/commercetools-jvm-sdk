package io.sphere.sdk.customobjects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class CustomObjectDraft<T> extends CustomObjectKey {
    private static final TypeReference<CustomObject<JsonNode>> JSON_NODE_TYPE_REFERENCE = new TypeReference<CustomObject<JsonNode>>() {
        @Override
        public String toString() {
            return "TypeReference<CustomObject<JsonNode>>";
        }
    };

    private final T value;
    private final Optional<Long> version;
    private final TypeReference<CustomObject<T>> typeReference;

    private CustomObjectDraft(final String container, final String key, final T value, final Optional<Long> version, final TypeReference<CustomObject<T>> typeReference) {
        super(container, key);
        this.value = value;
        this.version = version;
        this.typeReference = typeReference;
    }

    public T getValue() {
        return value;
    }

    public TypeReference<CustomObject<T>> typeReference() {
        return typeReference;
    }

    public static <T> CustomObjectDraft<T> ofVersionedDraft(final CustomObject<T> customObject, final T newValue, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectDraft<>(customObject.getContainer(), customObject.getKey(), newValue, Optional.of(customObject.getVersion()), typeReference);
    }

    public static <T> CustomObjectDraft<T> ofUnversionedDraft(final CustomObject<T> customObject, final T newValue, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectDraft<>(customObject.getContainer(), customObject.getKey(), newValue, Optional.empty(), typeReference);
    }

    public static <T> CustomObjectDraft<T> of(final String container, final String key, final T value, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectDraft<>(container, key, value, Optional.empty(), typeReference);
    }

    public static <T> CustomObjectDraft<T> of(final String container, final String key, final T value, final long version, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectDraft<>(container, key, value, Optional.of(version), typeReference);
    }

    public Optional<Long> getVersion() {
        return version;
    }

    public static CustomObjectDraft<JsonNode> of(final String container, final String key, final JsonNode node) {
        return of(container, key, node, JSON_NODE_TYPE_REFERENCE);
    }

    public CustomObjectDraft<T> withVersion(final long version) {
        return new CustomObjectDraft<>(getContainer(), getKey(), getValue(), Optional.of(version), typeReference());
    }
}
