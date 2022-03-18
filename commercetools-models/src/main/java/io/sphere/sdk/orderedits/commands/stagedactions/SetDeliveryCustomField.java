package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

public final class SetDeliveryCustomField extends OrderEditStagedUpdateActionBase {

    private final String deliveryId;

    private final String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final JsonNode value;

    @JsonCreator
    private SetDeliveryCustomField(final String name, @Nullable final JsonNode value, final String deliveryId) {
        super("setDeliveryCustomField");
        this.name = name;
        this.value = value;
        this.deliveryId = deliveryId;
    }

    public static SetDeliveryCustomField of(final String name, @Nullable final JsonNode value, final String deliveryId) {
        return new SetDeliveryCustomField(name, value, deliveryId);
    }

    public static SetDeliveryCustomField ofJson(final String name, final JsonNode value, final String deliveryId) {
        return new SetDeliveryCustomField(name, value, deliveryId);
    }

    public static SetDeliveryCustomField ofObject(final String name, final Object value, final String deliveryId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, deliveryId);
    }

    public static SetDeliveryCustomField ofUnset(final String name, final String deliveryId) {
        return ofJson(name, null, deliveryId);
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public JsonNode getValue() {
        return value;
    }
}