package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

import java.util.List;


final class ProductDiscountUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<ProductDiscount, ProductDiscountUpdateCommand, ProductDiscountExpansionModel<ProductDiscount>> implements ProductDiscountUpdateCommand {
    ProductDiscountUpdateCommandImpl(final Versioned<ProductDiscount> versioned, final List<? extends UpdateAction<ProductDiscount>> updateActions) {
        super(versioned, updateActions, ProductDiscountEndpoint.ENDPOINT, ProductDiscountUpdateCommandImpl::new, ProductDiscountExpansionModel.of());
    }

    ProductDiscountUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<ProductDiscount, ProductDiscountUpdateCommand, ProductDiscountExpansionModel<ProductDiscount>> builder) {
        super(builder);
    }
}
