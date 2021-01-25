package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFromCartDraft;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

final class OrderFromCartCreateCommandImpl extends MetaModelCreateCommandImpl<Order, OrderFromCartCreateCommand, OrderFromCartDraft, OrderExpansionModel<Order>> implements OrderFromCartCreateCommand {
    OrderFromCartCreateCommandImpl(final MetaModelCreateCommandBuilder<Order, OrderFromCartCreateCommand, OrderFromCartDraft, OrderExpansionModel<Order>> builder) {
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

    public static OrderFromCartCreateCommandImpl of(final ResourceIdentifier<Cart> cartResourceIdentifier, Long version) {
        return new OrderFromCartCreateCommandImpl(OrderFromCartDraft.of(cartResourceIdentifier, version));
    }

    @Override
    public OrderFromCartCreateCommand withDraft(final OrderFromCartDraft draft) {
        return new OrderFromCartCreateCommandImpl(copyBuilder().draft(draft));
    }
}
