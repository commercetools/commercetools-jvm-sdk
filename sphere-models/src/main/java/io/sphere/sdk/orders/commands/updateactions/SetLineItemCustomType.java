package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Sets or removes a custom type in a line item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetLineItemCustomType extends SetCustomTypeBase<Order> {
    private final String lineItemId;

    private SetLineItemCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String lineItemId) {
        super("setLineItemCustomType", typeId, typeKey, fields);
        this.lineItemId = lineItemId;
    }

    public static SetLineItemCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String lineItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, lineItemId);
    }

    public static SetLineItemCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String lineItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, lineItemId);
    }

    public static SetLineItemCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String lineItemId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, lineItemId);
    }

    public static SetLineItemCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String lineItemId) {
        return new SetLineItemCustomType(typeId, null, fields, lineItemId);
    }

    public static SetLineItemCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String lineItemId) {
        return new SetLineItemCustomType(null, typeKey, fields, lineItemId);
    }

    public static SetLineItemCustomType ofRemoveType(final String lineItemId) {
        return new SetLineItemCustomType(null, null, null, lineItemId);
    }

    public String getLineItemId() {
        return lineItemId;
    }
}
