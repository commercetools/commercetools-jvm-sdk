package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandTest#execution()}
 */
final class ProductDiscountCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<ProductDiscount, ProductDiscountCreateCommand, ProductDiscountDraft, ProductDiscountExpansionModel<ProductDiscount>> implements ProductDiscountCreateCommand {
    ProductDiscountCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<ProductDiscount, ProductDiscountCreateCommand, ProductDiscountDraft, ProductDiscountExpansionModel<ProductDiscount>> builder) {
        super(builder);
    }

    ProductDiscountCreateCommandImpl(final ProductDiscountDraft draft) {
        super(draft, ProductDiscountEndpoint.ENDPOINT, ProductDiscountExpansionModel.of(), ProductDiscountCreateCommandImpl::new);
    }
}
