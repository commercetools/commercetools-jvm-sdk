package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as = ResourceIdentifierImpl.class)
public interface ResourceIdentifier<T> extends ResourceIdentifiable<T> {
    @Nullable
    String getId();

    @Nullable
    String getKey();

    @Nullable
    String getTypeId();

    @Override
    default ResourceIdentifier<T> toResourceIdentifier() {
        return this;
    }

    static <T> ResourceIdentifier<T> ofKey(final String key) {
        return ofKey(key, null);
    }

    static <T> ResourceIdentifier<T> ofKey(final String key, final String typeId) {
        return ofIdOrKey(null, key, typeId);
    }

    static <T> ResourceIdentifier<T> ofId(final String id) {
        return ofId(id, null);
    }

    static <T> ResourceIdentifier<T> ofId(final String id, final String typeId) {
        return ofIdOrKey(id, null, typeId);
    }

    static <T> ResourceIdentifier<T> ofIdOrKey(@Nullable final String id, @Nullable final String key) {
        return ofIdOrKey(id, key, null);
    }

    static <T> ResourceIdentifier<T> ofIdOrKey(@Nullable final String id, @Nullable final String key, @Nullable final String typeId) {
        return new ResourceIdentifierImpl<>(id, key, typeId);
    }
}
