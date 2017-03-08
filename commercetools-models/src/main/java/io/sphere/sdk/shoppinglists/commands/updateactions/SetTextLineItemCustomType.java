package io.sphere.sdk.shoppinglists.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Sets or removes a custom type in a text line item.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.TextLineItemCustomFieldsIntegrationTest#setCustomType()}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetTextLineItemCustomType extends SetCustomTypeBase<ShoppingList> {
    private final String textLineItemId;

    private SetTextLineItemCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String textLineItemId) {
        super("setTextLineItemCustomType", typeId, typeKey, fields);
        this.textLineItemId = textLineItemId;
    }

    public static SetTextLineItemCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String textLineItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, textLineItemId);
    }

    public static SetTextLineItemCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String textLineItemId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, textLineItemId);
    }

    public static SetTextLineItemCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String textLineItemId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, textLineItemId);
    }

    public static SetTextLineItemCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String textLineItemId) {
        return new SetTextLineItemCustomType(typeId, null, fields, textLineItemId);
    }

    public static SetTextLineItemCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String textLineItemId) {
        return new SetTextLineItemCustomType(null, typeKey, fields, textLineItemId);
    }

    public static SetTextLineItemCustomType ofRemoveType(final String textLineItemId) {
        return new SetTextLineItemCustomType(null, null, null, textLineItemId);
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }
}
