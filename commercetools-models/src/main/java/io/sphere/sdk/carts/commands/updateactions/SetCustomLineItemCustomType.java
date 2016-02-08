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
public final class SetCustomLineItemCustomType extends SetCustomTypeBase<Cart> {
    private final String customLineItemId;

    private SetCustomLineItemCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String customLineItemId) {
        super("setCustomLineItemCustomType", typeId, typeKey, fields);
        this.customLineItemId = customLineItemId;
    }

    public static SetCustomLineItemCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String customLineItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, customLineItemId);
    }

    public static SetCustomLineItemCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String customLineItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, customLineItemId);
    }

    public static SetCustomLineItemCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String customLineItemId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, customLineItemId);
    }

    public static SetCustomLineItemCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String customLineItemId) {
        return new SetCustomLineItemCustomType(typeId, null, fields, customLineItemId);
    }

    public static SetCustomLineItemCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String customLineItemId) {
        return new SetCustomLineItemCustomType(null, typeKey, fields, customLineItemId);
    }

    public static SetCustomLineItemCustomType ofRemoveType(final String customLineItemId) {
        return new SetCustomLineItemCustomType(null, null, null, customLineItemId);
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}
