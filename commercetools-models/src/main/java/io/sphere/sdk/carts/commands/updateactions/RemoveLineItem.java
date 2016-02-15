package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 Decreases the quantity of the given line item. If after the update the quantity of the line item is not greater than 0 or the quantity is not specified, the line item is removed from the cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#removeLineItem()}
 */
public final class RemoveLineItem extends UpdateActionImpl<Cart> {
    private final String lineItemId;
    @Nullable
    private final Long quantity;

    private RemoveLineItem(final String lineItemId, @Nullable final Long quantity) {
        super("removeLineItem");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
    }

    public static RemoveLineItem of(final String lineItemId, @Nullable final Long quantity) {
        return new RemoveLineItem(lineItemId, quantity);
    }

    public static RemoveLineItem of(final String lineItemId) {
        return of(lineItemId, null);
    }

    public static RemoveLineItem of(final LineItem lineItem, @Nullable final Long quantity) {
        return of(lineItem.getId(), quantity);
    }

    public static RemoveLineItem of(final LineItem lineItem) {
        return of(lineItem.getId());
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public Long getQuantity() {
        return quantity;
    }
}
