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
public final class SetDeliveryAddressCustomType extends SetCustomTypeBase<Cart> {
    private final String deliveryId;

    private SetDeliveryAddressCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String deliveryId) {
        super("setDeliveryAddressCustomType", typeId, typeKey, fields);
        this.deliveryId = deliveryId;
    }

    public static SetDeliveryAddressCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String deliveryId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, deliveryId);
    }

    public static SetDeliveryAddressCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String deliveryId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, deliveryId);
    }

    public static SetDeliveryAddressCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String deliveryId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, deliveryId);
    }

    public static SetDeliveryAddressCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String deliveryId) {
        return new SetDeliveryAddressCustomType(typeId, null, fields, deliveryId);
    }

    public static SetDeliveryAddressCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String deliveryId) {
        return new SetDeliveryAddressCustomType(null, typeKey, fields, deliveryId);
    }

    public static SetDeliveryAddressCustomType ofRemoveType(final String deliveryId) {
        return new SetDeliveryAddressCustomType(null, null, null, deliveryId);
    }

    public String getDeliveryId() {
        return deliveryId;
    }
}
