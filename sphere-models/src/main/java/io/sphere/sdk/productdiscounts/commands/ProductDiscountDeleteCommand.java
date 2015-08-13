package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

public interface ProductDiscountDeleteCommand extends ByIdDeleteCommand<ProductDiscount>, MetaModelExpansionDsl<ProductDiscount, ProductDiscountDeleteCommand, ProductDiscountExpansionModel<ProductDiscount>> {
    static ProductDiscountDeleteCommand of(final Versioned<ProductDiscount> versioned) {
        return new ProductDiscountDeleteCommandImpl(versioned);
    }
}
