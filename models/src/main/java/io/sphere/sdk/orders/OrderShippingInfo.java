package io.sphere.sdk.orders;

import io.sphere.sdk.carts.CartShippingInfo;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;

public class OrderShippingInfo extends CartShippingInfo {
    private final List<Delivery> deliveries;

    private OrderShippingInfo(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, final Optional<Reference<ShippingMethod>> shippingMethod, final List<Delivery> deliveries) {
        super(shippingMethodName, price, shippingRate, taxRate, taxCategory, shippingMethod);
        this.deliveries = deliveries;
    }

    public static OrderShippingInfo of(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, final Optional<Reference<ShippingMethod>> shippingMethod, final List<Delivery> deliveries) {
        return new OrderShippingInfo(shippingMethodName, price, shippingRate, taxRate, taxCategory, shippingMethod, deliveries);
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }
}
