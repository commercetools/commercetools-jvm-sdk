package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.Nullable;

public final class SetBillingAddressCustomField extends OrderEditSetCustomFieldBase {

    private SetBillingAddressCustomField(final String name, final JsonNode value) {
        super("setBillingAddressCustomField", name, value);
    }

    public static SetBillingAddressCustomField of(final String name, @Nullable JsonNode value) {
        return new SetBillingAddressCustomField(name, value);
    }
}
