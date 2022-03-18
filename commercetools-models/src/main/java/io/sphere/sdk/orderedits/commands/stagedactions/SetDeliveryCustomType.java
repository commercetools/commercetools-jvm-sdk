package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public final class SetDeliveryCustomType extends OrderEditStagedUpdateActionBase {

    private final String deliveryId;

    @Nullable
    private final ResourceIdentifier<Type> type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Map<String, JsonNode> fields;

    private SetDeliveryCustomType(final String deliveryId, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("setDeliveryCustomType");
        this.deliveryId = deliveryId;
        this.type = ResourceIdentifier.ofIdOrKey(typeId, typeKey);
        this.fields = fields;
    }

    public static SetDeliveryCustomType of(final String deliveryId, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        return new SetDeliveryCustomType(deliveryId, typeId, typeKey, fields);
    }

    public static SetDeliveryCustomType of(final String deliveryId){
        return new SetDeliveryCustomType(deliveryId, null, null, null);
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

    public String getDeliveryId() {
        return deliveryId;
    }
}