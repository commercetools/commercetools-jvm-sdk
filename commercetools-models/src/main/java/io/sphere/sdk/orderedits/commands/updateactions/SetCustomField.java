package io.sphere.sdk.orderedits.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

public final class SetCustomField extends SetCustomFieldBase<OrderEdit> {

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
}
