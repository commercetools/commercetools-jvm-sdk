package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;

/** Represents shipping information in a {@link LineItemContainer}. */
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

    /** The shipping rate that is used to determine the cost of the shipping. */
    @Nonnull public ShippingRate getShippingRate() { return shippingRate; }

    /** Tracking data. */
    public String getTrackingData() { return trackingData; }

    /** A reference to the shipping method. null if custom shipping method was used. */
    public Reference<ShippingMethod> getShippingMethod() { return shippingMethod; }
}
