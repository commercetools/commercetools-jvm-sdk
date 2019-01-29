package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.commands.StagedUpdateActionImpl;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orderedits.OrderEdit;

import javax.annotation.Nullable;

public final class SetShippingAddress extends StagedUpdateActionImpl<OrderEdit> {

    @Nullable
    private final Address address;

    private SetShippingAddress(@Nullable final Address address) {
        super("setShippingAddress");
        this.address = address;
    }

    public static SetShippingAddress of(@Nullable final Address address) {
        return new SetShippingAddress(address);
    }

    @Nullable
    public Address getAddress() {
        return address;
    }

}