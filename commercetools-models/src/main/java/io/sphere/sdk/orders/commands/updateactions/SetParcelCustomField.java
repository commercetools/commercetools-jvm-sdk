package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

/**
 * Updates a custom field in a parcel.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetParcelCustomField extends SetCustomFieldBase<Order> {
    private final String parcelId;

    private SetParcelCustomField(final String name, final JsonNode value, final String parcelId) {
        super("setParcelCustomField", name, value);
        this.parcelId = parcelId;
    }

    public static SetParcelCustomField ofJson(final String name, final JsonNode value, final String parcelId) {
        return new SetParcelCustomField(name, value, parcelId);
    }

    public static SetParcelCustomField ofObject(final String name, final Object value, final String parcelId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, parcelId);
    }

    public static SetParcelCustomField ofUnset(final String name, final String parcelId) {
        return ofJson(name, null, parcelId);
    }

    public String getParcelId() {
        return parcelId;
    }
}
