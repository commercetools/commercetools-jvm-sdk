package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

final class CartDiscountDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<CartDiscount, CartDiscountDeleteCommand, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountDeleteCommand {
    CartDiscountDeleteCommandImpl(final Versioned<CartDiscount> versioned) {
        super(versioned, CartDiscountEndpoint.ENDPOINT, CartDiscountExpansionModel.of(), CartDiscountDeleteCommandImpl::new);
    }

    CartDiscountDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<CartDiscount, CartDiscountDeleteCommand, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }
}
