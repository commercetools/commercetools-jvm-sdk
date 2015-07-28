package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandTest#execution()}
 */
final class ProductDiscountCreateCommandImpl extends CreateCommandImpl<ProductDiscount, ProductDiscountDraft> implements ProductDiscountCreateCommand {
    ProductDiscountCreateCommandImpl(final ProductDiscountDraft draft) {
        super(draft, ProductDiscountEndpoint.ENDPOINT);
    }
}
