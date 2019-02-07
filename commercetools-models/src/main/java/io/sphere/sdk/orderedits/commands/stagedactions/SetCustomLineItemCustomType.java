package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public final class SetCustomLineItemCustomType extends OrderEditStagedUpdateActionBase {

    private final String customLineItemId;
    @Nullable
    private final ResourceIdentifier<Type> type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Map<String, JsonNode> fields;

    @JsonCreator
    private SetCustomLineItemCustomType(String customLineItemId, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        super("setCustomLineItemCustomType");
        this.customLineItemId = customLineItemId;
        this.type = type;
        this.fields = fields;
    }

    public static SetCustomLineItemCustomType of(String customLineItemId, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        return new SetCustomLineItemCustomType(customLineItemId, type, fields);
    }

    @Nullable
    public ResourceIdentifier<Type> getType() {
        return type;
    }

    @Nullable
    public Map<String, JsonNode> getFields() {
        return fields;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    protected static Map<String, JsonNode> mapObjectToJsonMap(final Map<String, Object> fields) {
        return fields.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> SphereJsonUtils.toJsonNode(entry.getValue())));
    }

}
