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
public final class SetShippingAddressCustomField extends SetCustomFieldBase<Cart> {
    private SetShippingAddressCustomField(final String name, final JsonNode value) {
        super("setShippingAddressCustomField", name, value);
    }

    public static SetShippingAddressCustomField ofJson(final String name, final JsonNode value) {
        return new SetShippingAddressCustomField(name, value);
    }

    public static SetShippingAddressCustomField ofObject(final String name, final Object value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode);
    }

    public static SetShippingAddressCustomField ofUnset(final String name) {
        return ofJson(name, null);
    }
}
