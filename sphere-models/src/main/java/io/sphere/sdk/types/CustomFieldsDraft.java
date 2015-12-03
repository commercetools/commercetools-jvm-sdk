package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.utils.MapUtils;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * @see Custom
 * @see CustomFieldsDraftBuilder
 */
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

    public static CustomFieldsDraft ofCustomFields(final CustomFields custom) {
        return CustomFieldsDraft.ofTypeIdAndJson(custom.getType().getTypeId(), custom.getFieldsJsonMap());
    }

    public static CustomFieldsDraft ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields) {
        requireNonNull(typeId);
        return new CustomFieldsDraft(typeId, null, fields);
    }

    public static CustomFieldsDraft ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson);
    }

    public static CustomFieldsDraft ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields) {
        requireNonNull(typeKey);
        return new CustomFieldsDraft(null, typeKey, fields);
    }

    public static CustomFieldsDraft ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson);
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

    private static Map<String, JsonNode> mapObjectToJsonMap(final Map<String, Object> fields) {
        return fields.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> SphereJsonUtils.toJsonNode(entry.getValue())));
    }
}
