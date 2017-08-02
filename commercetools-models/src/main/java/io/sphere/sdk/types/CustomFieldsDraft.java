package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;
import java.util.Map;

import static io.sphere.sdk.types.CustomFieldsDraftImpl.mapObjectToJsonMap;
import static java.util.Objects.requireNonNull;

/**
 * Draft for {@link CustomFields}.
 * @see CustomFieldsDraftBuilder
 */
@JsonDeserialize(as = CustomFieldsDraftImpl.class)
public interface CustomFieldsDraft {
    ResourceIdentifier<Type> getType();

    @Nullable
    Map<String, JsonNode> getFields();

    static CustomFieldsDraft ofCustomFields(final CustomFields custom) {
        return ofTypeIdAndJson(custom.getType().getId(), custom.getFieldsJsonMap());
    }

    static CustomFieldsDraft ofTypeIdAndJson(final String id, final Map<String, JsonNode> fields) {
        requireNonNull(id);
        return new CustomFieldsDraftImpl(id, null, fields);
    }

    static CustomFieldsDraft ofTypeIdAndObjects(final String id, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(id, fieldsJson);
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
