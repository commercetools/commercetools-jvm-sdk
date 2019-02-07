package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Address;

public final class AddItemShippingAddress extends OrderEditStagedUpdateActionBase {

    private final Address address;

    @JsonCreator
    private AddItemShippingAddress(final Address address){
        super("addItemShippingAddress");
        this.address = address;
    }

    public static AddItemShippingAddress of(final Address address) {
        return new AddItemShippingAddress(address);
    }

    public Address getAddress() {
        return address;
    }
}
