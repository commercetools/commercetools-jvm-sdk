package io.sphere.sdk.models;

import javax.annotation.Nullable;

public interface ResourceIdentifier {
    @Nullable
    String getId();

    @Nullable
    String getKey();

    @Nullable
    String getTypeId();

    static ResourceIdentifier ofKey(final String key) {
        return ofKey(key, null);
    }

    static ResourceIdentifier ofKey(final String key, final String typeId) {
        return ofIdOrKey(null, key, typeId);
    }

    static ResourceIdentifier ofId(final String id) {
        return ofId(id, null);
    }

    static ResourceIdentifier ofId(final String id, final String typeId) {
        return ofIdOrKey(id, null, typeId);
    }

    static ResourceIdentifier ofIdOrKey(@Nullable final String id, @Nullable final String key) {
        return ofIdOrKey(id, key, null);
    }

    static ResourceIdentifier ofIdOrKey(@Nullable final String id, @Nullable final String key, @Nullable final String typeId) {
        return new ResourceIdentifierImpl(id, key, typeId);
    }
}
