package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;

public interface ProductDiscountDeleteCommand extends ByIdDeleteCommand<ProductDiscount> {
    static DeleteCommand<ProductDiscount> of(final Versioned<ProductDiscount> versioned) {
        return new ProductDiscountDeleteCommandImpl(versioned);
    }
}
