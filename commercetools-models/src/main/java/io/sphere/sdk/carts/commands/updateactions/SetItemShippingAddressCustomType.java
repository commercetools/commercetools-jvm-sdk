package io.sphere.sdk.carts.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Sets or removes a custom type in a custom line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetItemShippingAddressCustomType extends SetCustomTypeBase<Cart> {
    private final String addressKey;

    private SetItemShippingAddressCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String addressKey) {
        super("setDeliveryAddressCustomType", typeId, typeKey, fields);
        this.addressKey = addressKey;
    }

    public static SetItemShippingAddressCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String addressKey) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, addressKey);
    }

    public static SetItemShippingAddressCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String addressKey) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, addressKey);
    }

    public static SetItemShippingAddressCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String addressKey) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, addressKey);
    }

    public static SetItemShippingAddressCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String addressKey) {
        return new SetItemShippingAddressCustomType(typeId, null, fields, addressKey);
    }

    public static SetItemShippingAddressCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String addressKey) {
        return new SetItemShippingAddressCustomType(null, typeKey, fields, addressKey);
    }

    public static SetItemShippingAddressCustomType ofRemoveType(final String addressKey) {
        return new SetItemShippingAddressCustomType(null, null, null, addressKey);
    }

    public String getAddressKey() {
        return addressKey;
    }
}
