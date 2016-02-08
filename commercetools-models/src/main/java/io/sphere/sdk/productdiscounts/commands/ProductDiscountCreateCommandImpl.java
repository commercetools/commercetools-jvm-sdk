package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandTest#execution()}
 */
final class ProductDiscountCreateCommandImpl extends MetaModelCreateCommandImpl<ProductDiscount, ProductDiscountCreateCommand, ProductDiscountDraft, ProductDiscountExpansionModel<ProductDiscount>> implements ProductDiscountCreateCommand {
    ProductDiscountCreateCommandImpl(final MetaModelCreateCommandBuilder<ProductDiscount, ProductDiscountCreateCommand, ProductDiscountDraft, ProductDiscountExpansionModel<ProductDiscount>> builder) {
        super(builder);
    }

    ProductDiscountCreateCommandImpl(final ProductDiscountDraft draft) {
        super(draft, ProductDiscountEndpoint.ENDPOINT, ProductDiscountExpansionModel.of(), ProductDiscountCreateCommandImpl::new);
    }
}
