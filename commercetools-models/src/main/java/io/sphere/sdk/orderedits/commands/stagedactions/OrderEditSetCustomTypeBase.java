package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class OrderEditSetCustomTypeBase extends OrderEditStagedUpdateActionBase {
    @Nullable
    protected final ResourceIdentifier<Type> type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    protected final Map<String, JsonNode> fields;

    protected OrderEditSetCustomTypeBase(String action, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        super(action);
        this.type = type;
        this.fields = fields;
    }

    protected static Map<String, JsonNode> mapObjectToJsonMap(final Map<String, Object> fields) {
        return fields.entrySet()
                     .stream()
                     .collect(Collectors.toMap(entry -> entry.getKey(),
                             entry -> SphereJsonUtils.toJsonNode(entry.getValue())));
    }

    @Nullable
    public ResourceIdentifier<Type> getType() {
        return type;
    }

    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }
}
