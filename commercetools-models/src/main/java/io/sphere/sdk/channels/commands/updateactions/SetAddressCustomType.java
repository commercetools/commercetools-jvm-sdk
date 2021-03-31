package io.sphere.sdk.channels.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.channels.Channel;
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
public final class SetAddressCustomType extends SetCustomTypeBase<Channel> {

    private SetAddressCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields) {
        super("setAddressCustomType", typeId, typeKey, fields);
    }

    public static SetAddressCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson);
    }

    public static SetAddressCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson);
    }

    public static SetAddressCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields);
    }

    public static SetAddressCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields) {
        return new SetAddressCustomType(typeId, null, fields);
    }

    public static SetAddressCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields) {
        return new SetAddressCustomType(null, typeKey, fields);
    }

    public static SetAddressCustomType ofRemoveType() {
        return new SetAddressCustomType(null, null, null);
    }
}
