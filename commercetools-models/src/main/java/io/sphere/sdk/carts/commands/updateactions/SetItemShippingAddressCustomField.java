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
public final class SetItemShippingAddressCustomField extends SetCustomFieldBase<Cart> {
    private final String addressKey;

    private SetItemShippingAddressCustomField(final String name, final JsonNode value, final String addressKey) {
        super("setItemShippingAddressCustomField", name, value);
        this.addressKey = addressKey;
    }

    public static SetItemShippingAddressCustomField ofJson(final String name, final JsonNode value, final String addressKey) {
        return new SetItemShippingAddressCustomField(name, value, addressKey);
    }

    public static SetItemShippingAddressCustomField ofObject(final String name, final Object value, final String addressKey) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, addressKey);
    }

    public static SetItemShippingAddressCustomField ofUnset(final String name, final String addressKey) {
        return ofJson(name, null, addressKey);
    }

    public String getAddressKey() {
        return addressKey;
    }
}
