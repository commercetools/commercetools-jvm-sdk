package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;

public final class SetDeliveryAddressCustomType extends OrderEditSetCustomTypeBase {

    private final String deliveryId;

    @JsonCreator
    private SetDeliveryAddressCustomType(String deliveryId, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        super("setDeliveryAddressCustomType", type, fields);
        this.deliveryId = deliveryId;
    }

    public static SetDeliveryAddressCustomType of(String deliveryId, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        return new SetDeliveryAddressCustomType(deliveryId, type, fields);
    }

    public String getDeliveryId() {
        return deliveryId;
    }

}
