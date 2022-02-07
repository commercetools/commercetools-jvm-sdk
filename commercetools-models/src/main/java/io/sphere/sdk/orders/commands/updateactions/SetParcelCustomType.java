package io.sphere.sdk.orders.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * Sets or removes a custom type in a parcel.
 *
 * {@doc.gen intro}
 *
 * @see io.sphere.sdk.types.Custom
 */
public final class SetParcelCustomType extends SetCustomTypeBase<Order> {
    private final String parcelId;

    private SetParcelCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String parcelId) {
        super("setParcelCustomType", typeId, typeKey, fields);
        this.parcelId = parcelId;
    }

    public static SetParcelCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String parcelId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, parcelId);
    }

    public static SetParcelCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String parcelId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, parcelId);
    }

    public static SetParcelCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String parcelId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, parcelId);
    }

    public static SetParcelCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String parcelId) {
        return new SetParcelCustomType(typeId, null, fields, parcelId);
    }

    public static SetParcelCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String parcelId) {
        return new SetParcelCustomType(null, typeKey, fields, parcelId);
    }

    public static SetParcelCustomType ofRemoveType(final String parcelId) {
        return new SetParcelCustomType(null, null, null, parcelId);
    }

    public String getParcelId() {
        return parcelId;
    }
}
