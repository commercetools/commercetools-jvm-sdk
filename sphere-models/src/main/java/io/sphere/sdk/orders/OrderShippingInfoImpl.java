package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.CartShippingInfoImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.List;

final class OrderShippingInfoImpl extends CartShippingInfoImpl implements OrderShippingInfo {
    private final List<Delivery> deliveries;

    @JsonCreator
    OrderShippingInfoImpl(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, final Reference<ShippingMethod> shippingMethod, final List<Delivery> deliveries) {
        super(shippingMethodName, price, shippingRate, taxRate, taxCategory, shippingMethod);
        this.deliveries = deliveries;
    }

    @Override
    public List<Delivery> getDeliveries() {
        return deliveries;
    }
}
