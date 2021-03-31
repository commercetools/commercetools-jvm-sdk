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
public final class SetBillingAddressCustomType extends SetCustomTypeBase<Cart> {

    private SetBillingAddressCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("setBillingAddressCustomType", typeId, typeKey, fields);
    }

    public static SetBillingAddressCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson);
    }

    public static SetBillingAddressCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson);
    }

    public static SetBillingAddressCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields);
    }

    public static SetBillingAddressCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields) {
        return new SetBillingAddressCustomType(typeId, null, fields);
    }

    public static SetBillingAddressCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields) {
        return new SetBillingAddressCustomType(null, typeKey, fields);
    }

    public static SetBillingAddressCustomType ofRemoveType() {
        return new SetBillingAddressCustomType(null, null, null);
    }
}
