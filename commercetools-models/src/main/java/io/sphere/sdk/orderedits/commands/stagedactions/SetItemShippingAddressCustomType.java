package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.Type;

import javax.annotation.Nullable;
import java.util.Map;

public final class SetItemShippingAddressCustomType extends OrderEditSetCustomTypeBase {

    private final String addressKey;

    @JsonCreator
    private SetItemShippingAddressCustomType(String addressKey, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        super("setDeliveryAddressCustomType", type, fields);
        this.addressKey = addressKey;
    }

    public static SetItemShippingAddressCustomType of(String addressKey, @Nullable ResourceIdentifier<Type> type, @Nullable Map<String, JsonNode> fields) {
        return new SetItemShippingAddressCustomType(addressKey, type, fields);
    }

    public String getAddressKey() {
        return addressKey;
    }

}
