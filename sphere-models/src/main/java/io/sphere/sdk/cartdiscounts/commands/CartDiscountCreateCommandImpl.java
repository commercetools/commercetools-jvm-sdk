package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;

final class CartDiscountCreateCommandImpl extends MetaModelCreateCommandImpl<CartDiscount, CartDiscountCreateCommand, CartDiscountDraft, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountCreateCommand {
    CartDiscountCreateCommandImpl(final MetaModelCreateCommandBuilder<CartDiscount, CartDiscountCreateCommand, CartDiscountDraft, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }

    CartDiscountCreateCommandImpl(final CartDiscountDraft draft) {
        super(draft, CartDiscountEndpoint.ENDPOINT, CartDiscountExpansionModel.of(), CartDiscountCreateCommandImpl::new);
    }
}
