package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandTest#execution()}
 */
public interface ProductDiscountCreateCommand extends CreateCommand<ProductDiscount> {
    static ProductDiscountCreateCommand of(final ProductDiscountDraft draft) {
        return new ProductDiscountCreateCommandImpl(draft);
    }
}
