package io.sphere.sdk.types.customupdateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SetCustomTypeBase<T> extends UpdateActionImpl<T> {
    @Nullable
    private final ResourceIdentifier<Type> type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Map<String, JsonNode> fields;

    protected SetCustomTypeBase(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        this("setCustomType", typeId, typeKey, fields);
    }

    protected SetCustomTypeBase(final String action, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super(action);
        this.type = ResourceIdentifier.ofIdOrKey(typeId, typeKey);
        this.fields = fields;
    }

    @Nullable
    public ResourceIdentifier<Type> getType() {
        return type;
    }

    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }


    protected static Map<String, JsonNode> mapObjectToJsonMap(final Map<String, Object> fields) {
        return fields.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> SphereJsonUtils.toJsonNode(entry.getValue())));
    }
}
