package io.sphere.sdk.customers.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customers.Customer;
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
public final class SetAddressCustomType extends SetCustomTypeBase<Customer> {
    private final String addressId;

    private SetAddressCustomType(final String addressId, @Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("setAddressCustomType", typeId, typeKey, fields);
        this.addressId = addressId;
    }

    public static SetAddressCustomType ofTypeKeyAndObjects(final String addressId, final String typeKey, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(addressId, typeKey, fieldsJson);
    }

    public static SetAddressCustomType ofTypeIdAndObjects(final String addressId, final String typeId, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(addressId, typeId, fieldsJson);
    }

    public static SetAddressCustomType ofTypeIdAndObjects(final String addressId, final String typeId, final String fieldName, final Object value) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(addressId, typeId, fields);
    }

    public static SetAddressCustomType ofTypeIdAndJson(final String addressId, final String typeId, final Map<String, JsonNode> fields) {
        return new SetAddressCustomType(addressId, typeId, null, fields);
    }

    public static SetAddressCustomType ofTypeKeyAndJson(final String addressId, final String typeKey, final Map<String, JsonNode> fields) {
        return new SetAddressCustomType(addressId, null, typeKey, fields);
    }

    public static SetAddressCustomType ofRemoveType(final String addressId) {
        return new SetAddressCustomType(addressId, null, null, null);
    }

    public String getAddressId() {
        return addressId;
    }
}
