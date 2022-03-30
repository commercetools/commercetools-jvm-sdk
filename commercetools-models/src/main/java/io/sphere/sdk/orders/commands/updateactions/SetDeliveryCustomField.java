package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a delivery.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetDeliveryCustomField extends SetCustomFieldBase<Order> {
    private final String deliveryId;

    private SetDeliveryCustomField(final String name, final JsonNode value, final String deliveryId) {
        super("setDeliveryCustomField", name, value);
        this.deliveryId = deliveryId;
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
}
