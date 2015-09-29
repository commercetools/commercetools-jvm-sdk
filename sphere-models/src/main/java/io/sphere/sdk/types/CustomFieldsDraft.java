package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.utils.MapUtils;

import javax.annotation.Nullable;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class CustomFieldsDraft {
    @Nullable
    private final String typeId;
    @Nullable
    private final String typeKey;
    private final Map<String, JsonNode> fields;

    CustomFieldsDraft(@Nullable final String typeId, @Nullable final String typeKey, final Map<String, JsonNode> fields) {
        this.typeId = typeId;
        this.typeKey = typeKey;
        this.fields = MapUtils.immutableCopyOf(fields);
    }

    public static CustomFieldsDraft ofTypeId(final String typeId, final Map<String, JsonNode> fields) {
        requireNonNull(typeId);
        return new CustomFieldsDraft(typeId, null, fields);
    }

    public static CustomFieldsDraft ofTypeKey(final String typeKey, final Map<String, JsonNode> fields) {
        requireNonNull(typeKey);
        return new CustomFieldsDraft(null, typeKey, fields);
    }

    @Nullable
    public String getTypeId() {
        return typeId;
    }

    @Nullable
    public String getTypeKey() {
        return typeKey;
    }

    public Map<String, JsonNode> getFields() {
        return fields;
    }
}
