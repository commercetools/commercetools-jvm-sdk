package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

final class CartDiscountUpdateCommandImpl extends UpdateCommandDslImpl<CartDiscount, CartDiscountUpdateCommand> implements CartDiscountUpdateCommand {
    CartDiscountUpdateCommandImpl(final Versioned<CartDiscount> versioned, final List<? extends UpdateAction<CartDiscount>> updateActions) {
        super(versioned, updateActions, CartDiscountEndpoint.ENDPOINT, CartDiscountUpdateCommandImpl::new);
    }

    CartDiscountUpdateCommandImpl(final UpdateCommandDslBuilder<CartDiscount, CartDiscountUpdateCommand> builder) {
        super(builder);
    }
}
