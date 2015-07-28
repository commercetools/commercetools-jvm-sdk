package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

final class CartUpdateCommandImpl extends UpdateCommandDslImpl<Cart, CartUpdateCommand> implements CartUpdateCommand {
    CartUpdateCommandImpl(final Versioned<Cart> versioned, final List<? extends UpdateAction<Cart>> updateActions) {
        super(versioned, updateActions, CartEndpoint.ENDPOINT, CartUpdateCommandImpl::new);
    }

    CartUpdateCommandImpl(final UpdateCommandDslBuilder<Cart, CartUpdateCommand> builder) {
        super(builder);
    }
}
