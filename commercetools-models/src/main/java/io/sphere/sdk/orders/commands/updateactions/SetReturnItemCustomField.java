package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a return item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetReturnItemCustomField extends SetCustomFieldBase<Order> {
    private final String returnItemId;

    private SetReturnItemCustomField(final String name, final JsonNode value, final String returnItemId) {
        super("setReturnItemCustomField", name, value);
        this.returnItemId = returnItemId;
    }

    public static SetReturnItemCustomField ofJson(final String name, final JsonNode value, final String returnItemId) {
        return new SetReturnItemCustomField(name, value, returnItemId);
    }

    public static SetReturnItemCustomField ofObject(final String name, final Object value, final String returnItemId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, returnItemId);
    }

    public static SetReturnItemCustomField ofUnset(final String name, final String returnItemId) {
        return ofJson(name, null, returnItemId);
    }

    public String getReturnItemId() {
        return returnItemId;
    }
}
