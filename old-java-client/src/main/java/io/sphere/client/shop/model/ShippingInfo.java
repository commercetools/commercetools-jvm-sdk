package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/** Represents full shipping information for a {@link Cart} or {@link Order}. */
public class ShippingInfo {
    @Nonnull private String shippingMethodName;
    @Nonnull private Money price;
    @Nonnull private ShippingRate shippingRate;
    @Nonnull private TaxRate taxRate;
    @Nonnull private final Reference<TaxCategory> taxCategory = EmptyReference.create("taxCategory");
    @Nonnull private List<Delivery> deliveries = newArrayList();
    //TODO why possibly not set...
    private Reference<ShippingMethod> shippingMethod = EmptyReference.create("shippingMethod");

    /** The name of the shipping method. */
    @Nonnull public String getShippingMethodName() { return shippingMethodName; }

    /** The cost of the shipping. */
    @Nonnull public Money getPrice() { return price; }

    /** The shipping rate that was used to determine the cost of the shipping. */
    @Nonnull public ShippingRate getShippingRate() { return shippingRate; }

    /** A reference to the shipping method. Null if custom shipping method was used. */
    public Reference<ShippingMethod> getShippingMethod() { return shippingMethod; }

    /** The tax rate of the shipping method. */
    @Nonnull public TaxRate getTaxRate() { return taxRate; }

    /** A reference to the tax category of the shipping method. */
    @Nonnull public Reference<TaxCategory> getTaxCategory() { return taxCategory; }

    @Nonnull
    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    @Override
    public String toString() {
        return "ShippingInfo{" +
                "shippingMethodName='" + shippingMethodName + '\'' +
                ", price=" + price +
                ", shippingRate=" + shippingRate +
                ", taxRate=" + taxRate +
                ", taxCategory=" + taxCategory +
                ", deliveries=" + deliveries +
                ", shippingMethod=" + shippingMethod +
                '}';
    }
}
