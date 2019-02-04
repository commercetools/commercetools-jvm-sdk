package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.commands.StagedUpdateActionBase;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orderedits.OrderEdit;

import javax.annotation.Nullable;

public final class SetBillingAddress extends StagedUpdateActionBase<OrderEdit> {

    @Nullable
    private final Address address;

    private SetBillingAddress(@Nullable final Address address) {
        super("setBillingAddress");
        this.address = address;
    }

    public static SetBillingAddress of(@Nullable final Address address) {
        return new SetBillingAddress(address);
    }

    @Nullable
    public Address getAddress() {
        return address;
    }


}