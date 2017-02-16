package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.LineItem;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Changes the line item quantity.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#changeLineItemQuantity()}
 *
 * @see ShoppingList#getLineItems()
 */
public final class ChangeLineItemQuantity extends UpdateActionImpl<ShoppingList> {
    private final String lineItemId;
    private final Long quantity;

    private ChangeLineItemQuantity(final String lineItemId, final Long quantity) {
        super("changeLineItemQuantity");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
    }

    public static ChangeLineItemQuantity of(final String lineItemId, final Long quantity) {
        return new ChangeLineItemQuantity(lineItemId, quantity);
    }

    public static ChangeLineItemQuantity of(final LineItem lineItem, final Long quantity) {
        return of(lineItem.getId(), quantity);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
