package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetCustomLineItemCustomField extends SetCustomFieldBase<Order> {
    private final String customLineItemId;

    private SetCustomLineItemCustomField(final String name, final JsonNode value, final String customLineItemId) {
        super("setCustomLineItemCustomField", name, value);
        this.customLineItemId = customLineItemId;
    }

    public static SetCustomLineItemCustomField ofJson(final String name, final JsonNode value, final String customLineItemId) {
        return new SetCustomLineItemCustomField(name, value, customLineItemId);
    }

    public static SetCustomLineItemCustomField ofObject(final String name, final Object value, final String customLineItemId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, customLineItemId);
    }

    public static SetCustomLineItemCustomField ofUnset(final String name, final String customLineItemId) {
        return ofJson(name, null, customLineItemId);
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}
