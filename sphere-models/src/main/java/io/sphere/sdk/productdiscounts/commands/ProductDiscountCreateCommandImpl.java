package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandTest#execution()}
 */
public final class ProductDiscountCreateCommandImpl extends CreateCommandImpl<ProductDiscount, ProductDiscountDraft> {
    private ProductDiscountCreateCommandImpl(final ProductDiscountDraft draft) {
        super(draft, ProductDiscountEndpoint.ENDPOINT);
    }

    public static ProductDiscountCreateCommandImpl of(final ProductDiscountDraft draft) {
        return new ProductDiscountCreateCommandImpl(draft);
    }
}
