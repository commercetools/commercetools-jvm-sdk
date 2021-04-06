package io.sphere.sdk.customers.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetAddressCustomField extends SetCustomFieldBase<Customer> {
    private final String addressId;

    private SetAddressCustomField(final String addressId, final String name, final JsonNode value) {
        super("setAddressCustomField", name, value);
        this.addressId = addressId;
    }

    public static SetAddressCustomField ofJson(final String addressId, final String name, final JsonNode value) {
        return new SetAddressCustomField(addressId, name, value);
    }

    public static SetAddressCustomField ofObject(final String addressId, final String name, final Object value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(addressId, name, jsonNode);
    }

    public static SetAddressCustomField ofUnset(final String addressId, final String name) {
        return ofJson(addressId, name, null);
    }
}
