package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;

import java.util.List;

final class CartDiscountUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<CartDiscount, CartDiscountUpdateCommand, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountUpdateCommand {
    CartDiscountUpdateCommandImpl(final Versioned<CartDiscount> versioned, final List<? extends UpdateAction<CartDiscount>> updateActions) {
        super(versioned, updateActions, CartDiscountEndpoint.ENDPOINT, CartDiscountUpdateCommandImpl::new, CartDiscountExpansionModel.of());
    }

    CartDiscountUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<CartDiscount, CartDiscountUpdateCommand, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }
}
