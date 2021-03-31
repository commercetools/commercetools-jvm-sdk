package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.Nullable;

public final class SetItemShippingAddressCustomField extends OrderEditSetCustomFieldBase {

    private final String addressKey;

    @JsonCreator
    private SetItemShippingAddressCustomField(final String addressKey, final String name, @Nullable JsonNode value) {
        super("setAddressKeyItemCustomField", name, value);
        this.addressKey = addressKey;
    }

    public static SetItemShippingAddressCustomField of(final String addressKey, final String name, @Nullable JsonNode value) {
        return new SetItemShippingAddressCustomField(addressKey, name, value);
    }

    public String getAddressKey() {
        return addressKey;
    }
}
