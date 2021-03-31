package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.Nullable;

public final class SetDeliveryAddressCustomField extends OrderEditSetCustomFieldBase {

    private final String deliveryId;

    @JsonCreator
    private SetDeliveryAddressCustomField(final String deliveryId, final String name, @Nullable JsonNode value) {
        super("setDeliveryAddressItemCustomField", name, value);
        this.deliveryId = deliveryId;
    }

    public static SetDeliveryAddressCustomField of(final String deliveryId, final String name, @Nullable JsonNode value) {
        return new SetDeliveryAddressCustomField(deliveryId, name, value);
    }

    public String getDeliveryId() {
        return deliveryId;
    }
}
