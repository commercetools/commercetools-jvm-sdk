package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class CustomFields extends Base {
    private final Reference<Type> type;
    private final Map<String, JsonNode> fields;

    private CustomFields(final Reference<Type> type, final Map<String, JsonNode> fields) {
        this.type = type;
        this.fields = fields;
    }

    @Nullable
    public JsonNode getField(final String name) {
        return fields.get(name);
    }

    @Nullable
    public <T> T getField(final String name, final TypeReference<T> typeReference) {
        return Optional.ofNullable(fields.get(name))
                .map(jsonNode -> SphereJsonUtils.readObject(jsonNode, typeReference))
                .orElse(null);
    }

    public Reference<Type> getType() {
        return type;
    }
}
