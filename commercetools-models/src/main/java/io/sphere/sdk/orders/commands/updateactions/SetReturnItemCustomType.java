package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Sets or removes a custom type in a return item.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetReturnItemCustomType extends SetCustomTypeBase<Order> {
    private final String returnItemId;

    private SetReturnItemCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String returnItemId) {
        super("setReturnItemCustomType", typeId, typeKey, fields);
        this.returnItemId = returnItemId;
    }

    public static SetReturnItemCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String returnItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, returnItemId);
    }

    public static SetReturnItemCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String returnItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, returnItemId);
    }

    public static SetReturnItemCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String returnItemId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, returnItemId);
    }

    public static SetReturnItemCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String returnItemId) {
        return new SetReturnItemCustomType(typeId, null, fields, returnItemId);
    }

    public static SetReturnItemCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String returnItemId) {
        return new SetReturnItemCustomType(null, typeKey, fields, returnItemId);
    }

    public static SetReturnItemCustomType ofRemoveType(final String returnItemId) {
        return new SetReturnItemCustomType(null, null, null, returnItemId);
    }

    public String getReturnItemId() {
        return returnItemId;
    }
}
