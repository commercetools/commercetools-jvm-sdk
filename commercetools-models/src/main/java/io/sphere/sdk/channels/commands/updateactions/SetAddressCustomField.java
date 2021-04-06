package io.sphere.sdk.channels.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetAddressCustomField extends SetCustomFieldBase<Channel> {
    private SetAddressCustomField(final String name, final JsonNode value) {
        super("setAddressCustomField", name, value);
    }

    public static SetAddressCustomField ofJson(final String name, final JsonNode value) {
        return new SetAddressCustomField(name, value);
    }

    public static SetAddressCustomField ofObject(final String name, final Object value) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode);
    }

    public static SetAddressCustomField ofUnset(final String name) {
        return ofJson(name, null);
    }
}
