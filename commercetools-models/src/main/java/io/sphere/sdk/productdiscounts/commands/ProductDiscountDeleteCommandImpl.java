package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

final class ProductDiscountDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<ProductDiscount, ProductDiscountDeleteCommand, ProductDiscountExpansionModel<ProductDiscount>> implements ProductDiscountDeleteCommand {
    ProductDiscountDeleteCommandImpl(final Versioned<ProductDiscount> versioned) {
        super(versioned, ProductDiscountEndpoint.ENDPOINT, ProductDiscountExpansionModel.of(), ProductDiscountDeleteCommandImpl::new);
    }

    ProductDiscountDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<ProductDiscount, ProductDiscountDeleteCommand, ProductDiscountExpansionModel<ProductDiscount>> builder) {
        super(builder);
    }
}
