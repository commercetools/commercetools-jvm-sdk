package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.List;

/**
 * Changes the order of the line items. The new order is defined by listing the ids of the line items.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#changeLineItemsOrder()}
 *
 * @see ShoppingList#getLineItems()
 */
public final class ChangeLineItemsOrder extends UpdateActionImpl<ShoppingList> {
    private final List<String> lineItemOrder;

    private ChangeLineItemsOrder(final List<String> textLineItemOrder) {
        super("changeLineItemsOrder");
        this.lineItemOrder = textLineItemOrder;
    }

    public List<String> getLineItemOrder() {
        return lineItemOrder;
    }

    public static ChangeLineItemsOrder of(final List<String> lineItemOrder) {
        return new ChangeLineItemsOrder(lineItemOrder);
    }
}
