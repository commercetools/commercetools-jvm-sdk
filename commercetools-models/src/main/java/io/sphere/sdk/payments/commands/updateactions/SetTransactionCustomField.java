package io.sphere.sdk.payments.commands.updateactions;

import com.fasterxml.jackson.databind.JsonNode;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.types.customupdateactions.SetCustomFieldBase;

public final class SetTransactionCustomField extends SetCustomFieldBase<Payment> {
    private final String transactionId;

    private SetTransactionCustomField(final String name, final JsonNode value, final String transactionId) {
        super("setTransactionCustomField", name, value);
        this.transactionId = transactionId;
    }

    public static SetTransactionCustomField ofJson(final String name, final JsonNode value, final String transactionId) {
        return new SetTransactionCustomField(name, value, transactionId);
    }

    public static SetTransactionCustomField ofObject(final String name, final Object value, final String transactionId) {
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(value);
        return ofJson(name, jsonNode, transactionId);
    }

    public static SetTransactionCustomField ofUnset(final String name, final String transactionId) {
        return ofJson(name, null, transactionId);
    }

    public String getTransactionId() {
        return transactionId;
    }
}
