package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Internal class to implement shipping infos
 */
public class CartShippingInfoImpl extends Base implements CartShippingInfo {
    private final String shippingMethodName;
    private final MonetaryAmount price;
    private final ShippingRate shippingRate;
    private final TaxRate taxRate;
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final Reference<ShippingMethod> shippingMethod;
    @Nullable
    private final TaxedItemPrice taxedPrice;

    @JsonCreator
    protected CartShippingInfoImpl(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, @Nullable final Reference<ShippingMethod> shippingMethod, @Nullable final TaxedItemPrice taxedPrice) {
        this.shippingMethodName = shippingMethodName;
        this.price = price;
        this.shippingRate = shippingRate;
        this.taxRate = taxRate;
        this.taxCategory = taxCategory;
        this.shippingMethod = shippingMethod;
        this.taxedPrice = taxedPrice;
    }

    @Override
    public String getShippingMethodName() {
        return shippingMethodName;
    }

    @Override
    public MonetaryAmount getPrice() {
        return price;
    }

    @Override
    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    @Override
    public TaxRate getTaxRate() {
        return taxRate;
    }

    @Override
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Override
    @Nullable
    public Reference<ShippingMethod> getShippingMethod() {
        return shippingMethod;
    }

    @Override
    @Nullable
    public TaxedItemPrice getTaxedPrice() {
        return taxedPrice;
    }
}
