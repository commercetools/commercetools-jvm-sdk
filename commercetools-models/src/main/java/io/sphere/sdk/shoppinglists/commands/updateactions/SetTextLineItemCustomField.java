package io.sphere.sdk.shoppinglists.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a text line item.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.TextLineItemCustomFieldsIntegrationTest#setCustomField()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetTextLineItemCustomField extends SetCustomFieldBase<ShoppingList> {
    private final String textLineItemId;

    private SetTextLineItemCustomField(final String name, final JsonNode value, final String textLineItemId) {
        super("setTextLineItemCustomField", name, value);
        this.textLineItemId = textLineItemId;
    }

    public static SetTextLineItemCustomField ofJson(final String name, final JsonNode value, final String textLineItemId) {
        return new SetTextLineItemCustomField(name, value, textLineItemId);
    }

    public static SetTextLineItemCustomField ofObject(final String name, final Object value, final String textLineItemId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, textLineItemId);
    }

    public static SetTextLineItemCustomField ofUnset(final String name, final String textLineItemId) {
        return ofJson(name, null, textLineItemId);
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }
}
