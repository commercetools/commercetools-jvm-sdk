package io.sphere.sdk.shoppinglists.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a line item.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.LineItemCustomFieldsIntegrationTest#setCustomField()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetLineItemCustomField extends SetCustomFieldBase<ShoppingList> {
    private final String lineItemId;

    private SetLineItemCustomField(final String name, final JsonNode value, final String lineItemId) {
        super("setLineItemCustomField", name, value);
        this.lineItemId = lineItemId;
    }

    public static SetLineItemCustomField ofJson(final String name, final JsonNode value, final String lineItemId) {
        return new SetLineItemCustomField(name, value, lineItemId);
    }

    public static SetLineItemCustomField ofObject(final String name, final Object value, final String lineItemId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, lineItemId);
    }

    public static SetLineItemCustomField ofUnset(final String name, final String lineItemId) {
        return ofJson(name, null, lineItemId);
    }

    public String getLineItemId() {
        return lineItemId;
    }
}
