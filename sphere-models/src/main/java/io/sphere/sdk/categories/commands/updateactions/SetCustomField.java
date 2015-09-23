package io.sphere.sdk.categories.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

public class SetCustomField extends SetCustomFieldBase<Category> {
    private SetCustomField(final String name, final JsonNode value) {
        super(name, value);
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

    /*

    action - String - "setCustomField"
name - String - Required
value - * - Optional

If value is absent or null, this field is removed if it exists.

If value is provided, set the  value of the field defined by the name.
The Field Definition determines the format for the value to be provided.
In particular, for the fields definitions, value has to provided a



     */
}
