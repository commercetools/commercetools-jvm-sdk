package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * @see Custom
 * @see CustomFieldsDraft
 */
public final class CustomFieldsDraftBuilder extends Base implements Builder<CustomFieldsDraft> {
    @Nullable
    private final String typeId;
    @Nullable
    private final String typeKey;
    private Map<String, JsonNode> fields;

    private CustomFieldsDraftBuilder(@Nullable final String typeId, @Nullable final String typeKey) {
        this.typeId = typeId;
        this.typeKey = typeKey;
        fields = new HashMap<>();
    }

    private CustomFieldsDraftBuilder(final CustomFieldsDraft customFieldsDraft) {
        typeId = customFieldsDraft.getType().getId();
        typeKey = customFieldsDraft.getType().getKey();
        fields = new HashMap<>(customFieldsDraft.getFields());
    }

    public static CustomFieldsDraftBuilder ofTypeId(final String typeId) {
        requireNonNull(typeId);
        return new CustomFieldsDraftBuilder(typeId, null);
    }

    public static CustomFieldsDraftBuilder ofTypeKey(final String typeKey) {
        requireNonNull(typeKey);
        return new CustomFieldsDraftBuilder(null, typeKey);
    }

    public static CustomFieldsDraftBuilder ofType(final Type type) {
        return ofTypeId(type.getId());
    }

    public static CustomFieldsDraftBuilder of(final CustomFieldsDraft customFieldsDraft) {
        return new CustomFieldsDraftBuilder(customFieldsDraft);
    }

    public static CustomFieldsDraftBuilder of(final CustomFields customFields) {
        final Reference<Type> type = customFields.getType();
        return new CustomFieldsDraftBuilder(type.getId(), type.getKey()).fields(customFields.getFieldsJsonMap());
    }

    public CustomFieldsDraftBuilder addObject(final String fieldName, final Object object) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(object);
        return addJsonField(fieldName, jsonNode);
    }

    public CustomFieldsDraftBuilder addObjects(final Map<String, Object> fields) {
        fields.entrySet().forEach(entry -> addObject(entry.getKey(), entry.getValue()));
        return this;
    }

    public CustomFieldsDraftBuilder fields(final Map<String, JsonNode> fields) {
        this.fields = fields;
        return this;
    }

    private CustomFieldsDraftBuilder addJsonField(final String fieldName, final JsonNode jsonNode) {
        fields.put(fieldName, jsonNode);
        return this;
    }

    @Override
    public CustomFieldsDraft build() {
        return new CustomFieldsDraftImpl(typeId, typeKey, fields);
    }

    @Deprecated
    public CustomFields buildFields() {
        return new CustomFieldsImpl(Reference.of(Type.referenceTypeId(), typeId), fields);
    }
}
