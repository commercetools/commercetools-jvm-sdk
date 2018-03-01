package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.HasUpdateAction;
import io.sphere.sdk.annotations.PropertySpec;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.carts.CartShippingInfo;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

@JsonDeserialize(as = OrderShippingInfoImpl.class)
@ResourceValue
public interface OrderShippingInfo extends CartShippingInfo {
    @HasUpdateAction(value = "removeDelivery", fields = {
            @PropertySpec(name = "deliveryId", type = String.class)
    })
    List<Delivery> getDeliveries();

    @Override
    MonetaryAmount getPrice();

    @Nullable
    @Override
    Reference<ShippingMethod> getShippingMethod();

    @Override
    String getShippingMethodName();

    @Override
    ShippingRate getShippingRate();

    @Override
    Reference<TaxCategory> getTaxCategory();

    @Override
    TaxRate getTaxRate();

    /**
     * Creates an {@link OrderShippingInfo} for the {@link OrderImportDraft}.
     *
     * @param shippingMethodName shippingMethodName
     * @param price price
     * @param shippingRate shippingRate
     * @param taxRate taxRate
     * @param taxCategory taxCategory
     * @param shippingMethod shippingMethod
     * @param deliveries deliveries
     * @return OrderShippingInfo
     */
    static OrderShippingInfo of(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, final Reference<ShippingMethod> shippingMethod, final List<Delivery> deliveries) {
        return new OrderShippingInfoImpl(deliveries, null, price, shippingMethod,  shippingMethodName, null, shippingRate, taxCategory, taxRate, null);
    }
}
