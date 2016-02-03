package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#changeLineItemQuantity()}
 */
public final class ChangeLineItemQuantity extends UpdateActionImpl<Cart> {
    private final String lineItemId;
    private final Long quantity;

    private ChangeLineItemQuantity(final String lineItemId, final Long quantity) {
        super("changeLineItemQuantity");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public static ChangeLineItemQuantity of(final String lineItemId, final long quantity) {
        return new ChangeLineItemQuantity(lineItemId, quantity);
    }

    public static UpdateAction<Cart> of(final LineItem lineItem, final long quantity) {
        return of(lineItem.getId(), quantity);
    }
}
