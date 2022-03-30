package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Sets or removes a custom type in a delivery
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetDeliveryCustomType extends SetCustomTypeBase<Order> {
    private final String deliveryId;

    private SetDeliveryCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String deliveryId) {
        super("setDeliveryCustomType", typeId, typeKey, fields);
        this.deliveryId = deliveryId;
    }

    public static SetDeliveryCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String deliveryId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, deliveryId);
    }

    public static SetDeliveryCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String deliveryId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, deliveryId);
    }

    public static SetDeliveryCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String deliveryId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, deliveryId);
    }

    public static SetDeliveryCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String deliveryId) {
        return new SetDeliveryCustomType(typeId, null, fields, deliveryId);
    }

    public static SetDeliveryCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String deliveryId) {
        return new SetDeliveryCustomType(null, typeKey, fields, deliveryId);
    }

    public static SetDeliveryCustomType ofRemoveType(final String deliveryId) {
        return new SetDeliveryCustomType(null, null, null, deliveryId);
    }

    public String getDeliveryId() {
        return deliveryId;
    }
}
