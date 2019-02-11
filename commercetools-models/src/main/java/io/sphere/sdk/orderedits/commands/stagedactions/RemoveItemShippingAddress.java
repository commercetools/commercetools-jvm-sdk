package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class RemoveItemShippingAddress extends OrderEditStagedUpdateActionBase {

    private final String addressKey;

    @JsonCreator
    private RemoveItemShippingAddress(final String addressKey) {
        super("removeItemShippingAddress");
        this.addressKey = addressKey;
    }

    public static RemoveItemShippingAddress of(final String addressKey) {
        return new RemoveItemShippingAddress(addressKey);
    }

    public String getAddressKey() {
        return addressKey;
    }
}
