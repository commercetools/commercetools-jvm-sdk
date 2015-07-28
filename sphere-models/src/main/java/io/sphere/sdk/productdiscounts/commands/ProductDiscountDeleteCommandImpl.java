package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;

public class ProductDiscountDeleteCommandImpl extends ByIdDeleteCommandImpl<ProductDiscount> {
    private ProductDiscountDeleteCommandImpl(final Versioned<ProductDiscount> versioned) {
        super(versioned, ProductDiscountEndpoint.ENDPOINT);
    }

    public static DeleteCommand<ProductDiscount> of(final Versioned<ProductDiscount> versioned) {
        return new ProductDiscountDeleteCommandImpl(versioned);
    }
}
