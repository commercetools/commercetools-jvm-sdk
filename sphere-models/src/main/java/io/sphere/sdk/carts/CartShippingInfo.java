package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.money.MonetaryAmount;
import java.util.Optional;

public class CartShippingInfo extends Base {
    private final String shippingMethodName;
    private final MonetaryAmount price;
    private final ShippingRate shippingRate;
    private final TaxRate taxRate;
    private final Reference<TaxCategory> taxCategory;
    private final Optional<Reference<ShippingMethod>> shippingMethod;

    @JsonCreator
    protected CartShippingInfo(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, final Optional<Reference<ShippingMethod>> shippingMethod) {
        this.shippingMethodName = shippingMethodName;
        this.price = price;
        this.shippingRate = shippingRate;
        this.taxRate = taxRate;
        this.taxCategory = taxCategory;
        this.shippingMethod = shippingMethod;
    }

    public static CartShippingInfo of(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, final Optional<Reference<ShippingMethod>> shippingMethod) {
        return new CartShippingInfo(shippingMethodName, price, shippingRate, taxRate, taxCategory, shippingMethod);
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    public Optional<Reference<ShippingMethod>> getShippingMethod() {
        return shippingMethod;
    }
}
