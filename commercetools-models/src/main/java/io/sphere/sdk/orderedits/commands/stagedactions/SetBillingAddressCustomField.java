package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

import javax.annotation.Nullable;

/**
 * Updates a custom field in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetBillingAddressCustomField extends OrderEditSetCustomFieldBase {

    private SetBillingAddressCustomField(final String name, final JsonNode value) {
        super("setBillingAddressCustomField", name, value);
    }

    public static SetBillingAddressCustomField of(final String name, @Nullable JsonNode value) {
        return new SetBillingAddressCustomField(name, value);
    }
}
