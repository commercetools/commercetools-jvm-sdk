package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Address;

public final class UpdateItemShippingAddress extends OrderEditStagedUpdateActionBase {

    private final Address address;

    @JsonCreator
    private UpdateItemShippingAddress(final Address address) {
        super("updateItemShippingAddress");
        this.address = address;
    }

    public static UpdateItemShippingAddress of(final Address address) {
        return new UpdateItemShippingAddress(address);
    }

    public Address getAddress() {
        return address;
    }
}
