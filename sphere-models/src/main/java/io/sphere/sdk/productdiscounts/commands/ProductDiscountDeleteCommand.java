package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;

public class ProductDiscountDeleteCommand extends ByIdDeleteCommandImpl<ProductDiscount> {
    private ProductDiscountDeleteCommand(final Versioned<ProductDiscount> versioned) {
        super(versioned, ProductDiscountEndpoint.ENDPOINT);
    }

    public static DeleteCommand<ProductDiscount> of(final Versioned<ProductDiscount> versioned) {
        return new ProductDiscountDeleteCommand(versioned);
    }
}
