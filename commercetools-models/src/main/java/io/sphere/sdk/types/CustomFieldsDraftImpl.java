package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.SphereInternalUtils.immutableCopyOf;

/**
 * @see Custom
 * @see CustomFieldsDraftBuilder
 */
final class CustomFieldsDraftImpl extends Base implements CustomFieldsDraft {
    private final ResourceIdentifier<Type> type;
    @Nullable
    private final Map<String, JsonNode> fields;

    CustomFieldsDraftImpl(@Nullable final String id, @Nullable final String typeKey, final Map<String, JsonNode> fields) {
        this.type = ResourceIdentifier.ofIdOrKey(id, typeKey);
        this.fields = immutableCopyOf(fields);
    }

    @Override
    public ResourceIdentifier<Type> getType() {
        return type;
    }

    @Override
    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }

    static Map<String, JsonNode> mapObjectToJsonMap(final Map<String, Object> fields) {
        return fields.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> SphereJsonUtils.toJsonNode(entry.getValue())));
    }
}
