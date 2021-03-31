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
public final class SetBillingAddressCustomField extends SetCustomFieldBase<Cart> {
    private SetBillingAddressCustomField(final String name, final JsonNode value) {
        super("setBillingAddressCustomField", name, value);
    }

    public static SetBillingAddressCustomField ofJson(final String name, final JsonNode value) {
        return new SetBillingAddressCustomField(name, value);
    }

    public static SetBillingAddressCustomField ofObject(final String name, final Object value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode);
    }

    public static SetBillingAddressCustomField ofUnset(final String name) {
        return ofJson(name, null);
    }
}
