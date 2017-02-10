package io.sphere.sdk.shoppinglists.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListCustomFieldsIntegrationTest#setCustomField()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetCustomField extends SetCustomFieldBase<ShoppingList> {

    private SetCustomField(final String name, final JsonNode value) {
        super("setCustomField", name, value);
    }

    public static SetCustomField ofJson(final String name, final JsonNode value) {
        return new SetCustomField(name, value);
    }

    public static SetCustomField ofObject(final String name, final Object value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode);
    }

    public static SetCustomField ofUnset(final String name) {
        return ofJson(name, null);
    }

}
