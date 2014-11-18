package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateAction;

/**
 Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart.

 {@include.example io.sphere.sdk.carts.CartIntegrationTest#changeLineItemQuantityUpdateAction()}
 */
public class ChangeLineItemQuantity extends UpdateAction<Cart> {
    private final String lineItemId;
    private final int quantity;

    private ChangeLineItemQuantity(final String lineItemId, final int quantity) {
        super("changeLineItemQuantity");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public static ChangeLineItemQuantity of(final String lineItemId, final int quantity) {
        return new ChangeLineItemQuantity(lineItemId, quantity);
    }

    public static UpdateAction<Cart> of(final LineItem lineItem, final int quantity) {
        return of(lineItem.getId(), quantity);
    }
}
