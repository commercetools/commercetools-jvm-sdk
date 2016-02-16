package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountValue;

/**
 * Changes the value of the discount.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandIntegrationTest#changeValue()}
 */
public final class ChangeValue extends UpdateActionImpl<ProductDiscount> {
    private final ProductDiscountValue value;

    private ChangeValue(final ProductDiscountValue value) {
        super("changeValue");
        this.value = value;
    }

    public static ChangeValue of(final ProductDiscountValue value) {
        return new ChangeValue(value);
    }

    public ProductDiscountValue getValue() {
        return value;
    }
}
