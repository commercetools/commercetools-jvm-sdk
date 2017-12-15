package io.sphere.sdk.discountcodes.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Map;

/**
 *
 * Sets/unsets the custom type.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.discountcodes.DiscountCodeCustomFieldsIntegrationTest#setCustomType()}
 *
 */
public final class SetCustomType extends SetCustomTypeBase<DiscountCode> {
    private SetCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super(typeId, typeKey, fields);
    }

    public static SetCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson);
    }

    public static SetCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson);
    }

    public static SetCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields) {
        return new SetCustomType(typeId, null, fields);
    }

    public static SetCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields) {
        return new SetCustomType(null, typeKey, fields);
    }

    public static SetCustomType ofRemoveType() {
        return new SetCustomType(null, null, null);
    }
}
