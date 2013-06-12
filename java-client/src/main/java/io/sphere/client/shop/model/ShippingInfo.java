package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;

/** Represents full shipping information for a {@link Cart} or {@link Order}. */
public class ShippingInfo {
    @Nonnull private String shippingMethodName;
    @Nonnull private Money price;
    @Nonnull private ShippingRate shippingRate;
    private String trackingData;
    //TODO why possibly not set...
    private Reference<ShippingMethod> shippingMethod = EmptyReference.create("shippingMethod");

    /** The name of the shipping method. */
    @Nonnull public String getShippingMethodName() { return shippingMethodName; }

    /** The cost of the shipping. */
    @Nonnull public Money getPrice() { return price; }

    /** The shipping rate that was used to determine the cost of the shipping. */
    @Nonnull public ShippingRate getShippingRate() { return shippingRate; }

    /** Tracking data is usually some info about the delivery (like a DHL tracking number) which is useful to keep an 
     * eye on your delivery, view its status etc. */
    public String getTrackingData() { return trackingData; }

    /** A reference to the shipping method. Null if custom shipping method was used. */
    public Reference<ShippingMethod> getShippingMethod() { return shippingMethod; }
}
