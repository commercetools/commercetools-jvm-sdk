package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;

final class ProductDiscountDeleteCommandImpl extends ByIdDeleteCommandImpl<ProductDiscount> {
    ProductDiscountDeleteCommandImpl(final Versioned<ProductDiscount> versioned) {
        super(versioned, ProductDiscountEndpoint.ENDPOINT);
    }
}
