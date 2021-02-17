package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.lang.reflect.Type;


public final class ReferencedResourceNotFoundError extends SphereError {

    public static final String CODE = "ReferencedResourceNotFound";

    private final Reference<Type> typeId;
    @Nullable
    private final String id;
    @Nullable
    private final String key;

    @JsonCreator
    private ReferencedResourceNotFoundError(final String message, final Reference<Type> typeId, @Nullable String id, @Nullable String key) {
        super(CODE, message);
        this.typeId = typeId;
        this.id = id;
        this.key = key;
    }

    public static ReferencedResourceNotFoundError of(final String message, final Reference<Type> typeId, @Nullable String id, @Nullable String key) {
        return new ReferencedResourceNotFoundError(message, typeId, id, key);
    }

    @Nullable
    public String getKey() {
        return key;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public Reference<Type> getTypeId() {
        return typeId;
    }
}
