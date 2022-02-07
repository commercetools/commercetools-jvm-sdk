package io.sphere.sdk.payments.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.types.customupdateactions.SetCustomTypeBase;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public final class SetTransactionCustomType extends SetCustomTypeBase<Payment> {
    private final String transactionId;

    private SetTransactionCustomType(@Nullable final String typeId, @Nullable final String typeKey, @Nullable final Map<String, JsonNode> fields, final String transactionId) {
        super("setTransactionCustomType", typeId, typeKey, fields);
        this.transactionId = transactionId;
    }


    public static SetTransactionCustomType ofTypeKeyAndObjects(final String typeKey, final Map<String, Object> fields, final String transactionId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeKeyAndJson(typeKey, fieldsJson, transactionId);
    }

    public static SetTransactionCustomType ofTypeIdAndObjects(final String typeId, final Map<String, Object> fields, final String transactionId) {
        final Map<String, JsonNode> fieldsJson = mapObjectToJsonMap(fields);
        return ofTypeIdAndJson(typeId, fieldsJson, transactionId);
    }

    public static SetTransactionCustomType ofTypeIdAndObjects(final String typeId, final String fieldName, final Object value, final String transactionId) {
        final Map<String, Object> fields = Collections.singletonMap(fieldName, value);
        return ofTypeIdAndObjects(typeId, fields, transactionId);
    }

    public static SetTransactionCustomType ofTypeIdAndJson(final String typeId, final Map<String, JsonNode> fields, final String transactionId) {
        return new SetTransactionCustomType(typeId, null, fields, transactionId);
    }

    public static SetTransactionCustomType ofTypeKeyAndJson(final String typeKey, final Map<String, JsonNode> fields, final String transactionId) {
        return new SetTransactionCustomType(null, typeKey, fields, transactionId);
    }

    public static SetTransactionCustomType ofRemoveType(final String transactionId) {
        return new SetTransactionCustomType(null, null, null, transactionId);
    }

    public String getTransactionId() {
        return transactionId;
    }
}
