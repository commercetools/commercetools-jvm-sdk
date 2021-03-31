package io.sphere.sdk.carts.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetDeliveryAddressCustomField extends SetCustomFieldBase<Cart> {
    private final String deliveryId;

    private SetDeliveryAddressCustomField(final String name, final JsonNode value, final String deliveryId) {
        super("setDeliveryAddressCustomField", name, value);
        this.deliveryId = deliveryId;
    }

    public static SetDeliveryAddressCustomField ofJson(final String name, final JsonNode value, final String deliveryId) {
        return new SetDeliveryAddressCustomField(name, value, deliveryId);
    }

    public static SetDeliveryAddressCustomField ofObject(final String name, final Object value, final String deliveryId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, deliveryId);
    }

    public static SetDeliveryAddressCustomField ofUnset(final String name, final String deliveryId) {
        return ofJson(name, null, deliveryId);
    }

    public String getDeliveryId() {
        return deliveryId;
    }
}
