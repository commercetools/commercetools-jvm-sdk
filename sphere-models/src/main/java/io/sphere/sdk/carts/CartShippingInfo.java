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
 * Information concerning shipping in a cart.
 *
 * @see io.sphere.sdk.carts.commands.updateactions.SetShippingAddress
 */
public class CartShippingInfo extends Base {
    private final String shippingMethodName;
    private final MonetaryAmount price;
    private final ShippingRate shippingRate;
    private final TaxRate taxRate;
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final Reference<ShippingMethod> shippingMethod;

    @JsonCreator
    protected CartShippingInfo(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, @Nullable final Reference<ShippingMethod> shippingMethod) {
        this.shippingMethodName = shippingMethodName;
        this.price = price;
        this.shippingRate = shippingRate;
        this.taxRate = taxRate;
        this.taxCategory = taxCategory;
        this.shippingMethod = shippingMethod;
    }

    public static CartShippingInfo of(final String shippingMethodName, final MonetaryAmount price, final ShippingRate shippingRate, final TaxRate taxRate, final Reference<TaxCategory> taxCategory, final Reference<ShippingMethod> shippingMethod) {
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

    @Nullable
    public Reference<ShippingMethod> getShippingMethod() {
        return shippingMethod;
    }
}
