package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public final class SetLineItemCustomType extends OrderEditStagedUpdateActionBase {

    private final String lineItemId;

    @Nullable
    private final ResourceIdentifier<Type> type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Map<String, JsonNode> fields;

    private SetLineItemCustomType(final String lineItemId, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("setLineItemCustomType");
        this.lineItemId = lineItemId;
        this.type = ResourceIdentifier.ofIdOrKey(typeId, typeKey);
        this.fields = fields;
    }

    public static SetLineItemCustomType of(final String lineItemId, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        return new SetLineItemCustomType(lineItemId, typeId, typeKey, fields);
    }

    public static SetLineItemCustomType of(final String lineItemId){
        return new SetLineItemCustomType(lineItemId, null, null, null);
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

    public String getLineItemId() {
        return lineItemId;
    }
}