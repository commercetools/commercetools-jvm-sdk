package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

public interface ProductDiscountDeleteCommand extends MetaModelExpansionDsl<ProductDiscount, ProductDiscountDeleteCommand, ProductDiscountExpansionModel<ProductDiscount>>, DeleteCommand<ProductDiscount> {
    static ProductDiscountDeleteCommand of(final Versioned<ProductDiscount> versioned) {
        return new ProductDiscountDeleteCommandImpl(versioned);
    }
}
