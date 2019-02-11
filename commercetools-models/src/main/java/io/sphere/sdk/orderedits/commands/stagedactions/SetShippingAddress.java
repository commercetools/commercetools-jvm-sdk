package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

public final class SetShippingAddress extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final Address address;

    @JsonCreator
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