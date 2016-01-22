package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.SphereInternalUtils.immutableCopyOf;
import static java.util.Objects.requireNonNull;

/**
 * @see Custom
 * @see CustomFieldsDraftBuilder
 */
public class CustomFieldsDraft {
    private final ResourceIdentifier<Type> type;
    @Nullable
    private final Map<String, JsonNode> fields;

    CustomFieldsDraft(@Nullable final String typeId, @Nullable final String typeKey, final Map<String, JsonNode> fields) {
        this.type = ResourceIdentifier.ofIdOrKey(typeId, typeKey);
        this.fields = immutableCopyOf(fields);
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

    /**
     * use {@link #getType()} instead
     * @return id of the type to set
     */
    @Deprecated
    @Nullable
    public String getTypeId() {
        return type.getTypeId();
    }

    /**
     * @deprecated use {@link #getType()} instead
     * @return key of the type to set
     */
    @Deprecated
    @Nullable
    public String getTypeKey() {
        return type.getKey();
    }

    public ResourceIdentifier<Type> getType() {
        return type;
    }

    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }

    private static Map<String, JsonNode> mapObjectToJsonMap(final Map<String, Object> fields) {
        return fields.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> SphereJsonUtils.toJsonNode(entry.getValue())));
    }
}
