package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFromCartDraft;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

final class OrderFromCartCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<Order, OrderFromCartCreateCommand, OrderFromCartDraft, OrderExpansionModel<Order>> implements OrderFromCartCreateCommand {
    OrderFromCartCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<Order, OrderFromCartCreateCommand, OrderFromCartDraft, OrderExpansionModel<Order>> builder) {
        super(builder);
    }

    OrderFromCartCreateCommandImpl(final OrderFromCartDraft draft) {
        super(draft, OrderEndpoint.ENDPOINT, OrderExpansionModel.of(), OrderFromCartCreateCommandImpl::new);
    }

    public static OrderFromCartCreateCommandImpl of(final OrderFromCartDraft draft) {
        return new OrderFromCartCreateCommandImpl(draft);
    }

    public static OrderFromCartCreateCommandImpl of(final Versioned<Cart> cart) {
        return new OrderFromCartCreateCommandImpl(OrderFromCartDraft.of(cart));
    }
}
