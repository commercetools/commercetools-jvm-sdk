package io.sphere.sdk.models;

import javax.annotation.Nullable;

final class ResourceIdentifierImpl<T> extends Base implements ResourceIdentifier<T> {
    @Nullable
    private final String id;
    @Nullable
    private final String key;
    @Nullable
    private final String typeId;

    public ResourceIdentifierImpl(@Nullable final String id, @Nullable final String key, @Nullable final String typeId) {
        this.id = id;
        this.key = key;
        this.typeId = typeId;
    }

    @Override
    @Nullable
    public String getId() {
        return id;
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
    }

    @Override
    @Nullable
    public String getTypeId() {
        return typeId;
    }
}
