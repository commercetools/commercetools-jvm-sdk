package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.*;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Sets the {@code address} value on an existing {@code Delivery}
 */
public final class SetDeliveryAddress extends UpdateActionImpl<Order> {

    private final String deliveryId;

    @Nullable
    private final Address address;

    private SetDeliveryAddress(final String deliveryId, @Nullable final Address address) {
        super("setDeliveryAddress");
        this.deliveryId = deliveryId;
        this.address = address;
    }

    public static SetDeliveryAddress of(final String deliveryId, @Nullable final Address address){
        return new SetDeliveryAddress(deliveryId,address);
    }

    public static SetDeliveryAddress ofUnset(final String deliveryId){
        return new SetDeliveryAddress(deliveryId,null);
    }

    @Nullable
    public Address getAddress() {
        return address;
    }

    public String getDeliveryId() {
        return deliveryId;
    }


}
