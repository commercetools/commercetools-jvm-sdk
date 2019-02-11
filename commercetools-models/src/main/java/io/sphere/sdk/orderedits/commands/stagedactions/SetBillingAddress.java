package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

public final class SetBillingAddress extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final Address address;

    @JsonCreator
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