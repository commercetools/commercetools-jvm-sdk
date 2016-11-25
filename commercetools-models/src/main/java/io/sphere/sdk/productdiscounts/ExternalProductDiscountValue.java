package io.sphere.sdk.productdiscounts;

import io.sphere.sdk.models.Base;

/**
 *
 * An external discount.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setDiscountedPrice()}
 *
 * @see io.sphere.sdk.productdiscounts.ProductDiscount
 * @see ProductDiscount#getValue()
 * @see io.sphere.sdk.products.commands.updateactions.SetDiscountedPrice
 */
public final class ExternalProductDiscountValue extends Base implements ProductDiscountValue {

    private ExternalProductDiscountValue() {
    }

    public static ExternalProductDiscountValue of() {
        return new ExternalProductDiscountValue();
    }
}
