package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.DraftBasedCreateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFromCartDraft;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

/**
 * Orders a cart.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderFromCartCreateCommandIntegrationTest#execution()}
 */
public interface OrderFromCartCreateCommand extends DraftBasedCreateCommandDsl<Order, OrderFromCartDraft, OrderFromCartCreateCommand>, MetaModelReferenceExpansionDsl<Order, OrderFromCartCreateCommand, OrderExpansionModel<Order>> {
    static OrderFromCartCreateCommand of(final OrderFromCartDraft draft) {
        return new OrderFromCartCreateCommandImpl(draft);
    }

    static OrderFromCartCreateCommand of(final ResourceIdentifier<Cart> cartResourceIdentifier, Long version) {
        return new OrderFromCartCreateCommandImpl(OrderFromCartDraft.of(cartResourceIdentifier, version));
    }

    static OrderFromCartCreateCommand of(final Versioned<Cart> cart) {
        return new OrderFromCartCreateCommandImpl(OrderFromCartDraft.of(cart));
    }
}
