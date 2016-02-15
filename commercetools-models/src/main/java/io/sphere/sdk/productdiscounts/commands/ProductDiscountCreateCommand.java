package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommandIntegrationTest#execution()}
 */
public interface ProductDiscountCreateCommand extends DraftBasedCreateCommand<ProductDiscount, ProductDiscountDraft>, MetaModelReferenceExpansionDsl<ProductDiscount, ProductDiscountCreateCommand, ProductDiscountExpansionModel<ProductDiscount>> {
    static ProductDiscountCreateCommand of(final ProductDiscountDraft draft) {
        return new ProductDiscountCreateCommandImpl(draft);
    }
}
