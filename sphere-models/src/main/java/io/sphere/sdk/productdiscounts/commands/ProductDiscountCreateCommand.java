package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandTest#execution()}
 */
public final class ProductDiscountCreateCommand extends CreateCommandImpl<ProductDiscount, ProductDiscountDraft> {
    private ProductDiscountCreateCommand(final ProductDiscountDraft draft) {
        super(draft, ProductDiscountEndpoint.ENDPOINT);
    }

    public static ProductDiscountCreateCommand of(final ProductDiscountDraft draft) {
        return new ProductDiscountCreateCommand(draft);
    }
}
