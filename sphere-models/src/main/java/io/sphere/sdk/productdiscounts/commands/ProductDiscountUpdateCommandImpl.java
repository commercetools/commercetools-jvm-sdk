package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.productdiscounts.ProductDiscount;

import java.util.List;


final class ProductDiscountUpdateCommandImpl extends UpdateCommandDslImpl<ProductDiscount, ProductDiscountUpdateCommand> implements ProductDiscountUpdateCommand {
    ProductDiscountUpdateCommandImpl(final Versioned<ProductDiscount> versioned, final List<? extends UpdateAction<ProductDiscount>> updateActions) {
        super(versioned, updateActions, ProductDiscountEndpoint.ENDPOINT, ProductDiscountUpdateCommandImpl::new);
    }

    ProductDiscountUpdateCommandImpl(final UpdateCommandDslBuilder<ProductDiscount, ProductDiscountUpdateCommand> builder) {
        super(builder);
    }
}
