package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;
import java.util.Map;

import static io.sphere.sdk.types.CustomFieldsDraftImpl.mapObjectToJsonMap;
import static java.util.Objects.requireNonNull;

@JsonDeserialize(as = CustomFieldsDraftImpl.class)
public interface CustomFieldsDraft {
    ResourceIdentifier<Type> getType();

    @Nullable
    Map<String, JsonNode> getFields();

    static CustomFieldsDraft ofCustomFields(final CustomFields custom) {
        return ofTypeIdAndJson(custom.getType().getTypeId(), custom.getFieldsJsonMap());
    }

    static CustomFieldsDraft ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields) {
        requireNonNull(typeId);
        return new CustomFieldsDraftImpl(typeId, null, fields);
    }

    static CustomFieldsDraft ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson);
    }

    static CustomFieldsDraft ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields) {
        requireNonNull(typeKey);
        return new CustomFieldsDraftImpl(null, typeKey, fields);
    }

    static CustomFieldsDraft ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson);
    }
}
