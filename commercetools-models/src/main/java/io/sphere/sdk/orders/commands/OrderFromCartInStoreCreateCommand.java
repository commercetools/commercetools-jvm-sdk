package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.DraftBasedCreateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFromCartDraft;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

public interface OrderFromCartInStoreCreateCommand extends DraftBasedCreateCommandDsl<Order, OrderFromCartDraft, OrderFromCartInStoreCreateCommand>, MetaModelReferenceExpansionDsl<Order, OrderFromCartInStoreCreateCommand, OrderExpansionModel<Order>> {

    static OrderFromCartInStoreCreateCommand of(final String storeKey, final OrderFromCartDraft draft) {
        return new OrderFromCartInStoreCreateCommandImpl(storeKey, draft);
    }

    static OrderFromCartInStoreCreateCommand of(final String storeKey, final Versioned<Cart> cart) {
        return new OrderFromCartInStoreCreateCommandImpl(storeKey, OrderFromCartDraft.of(cart));
    }

    static OrderFromCartInStoreCreateCommand of(final String storeKey, final ResourceIdentifier<Cart> cartResourceIdentifier, Long version) {
        return new OrderFromCartInStoreCreateCommandImpl(storeKey, OrderFromCartDraft.of(cartResourceIdentifier, version));
    }
}
