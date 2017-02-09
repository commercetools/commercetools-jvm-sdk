package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;

import java.util.List;

/**
 * Changes the order of the text line items. The new order is defined by listing the ids of the text line items.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#changeTextLineItemsOrder()}
 *
 * @see ShoppingList#getTextLineItems()
 */
public final class ChangeTextLineItemsOrder extends UpdateActionImpl<ShoppingList> {
    private final List<String> textLineItemOrder;

    private ChangeTextLineItemsOrder(final List<String> textLineItemOrder) {
        super("changeTextLineItemsOrder");
        this.textLineItemOrder = textLineItemOrder;
    }

    public List<String> getTextLineItemOrder() {
        return textLineItemOrder;
    }

    public static ChangeTextLineItemsOrder of(final List<String> textLineItemOrder) {
        return new ChangeTextLineItemsOrder(textLineItemOrder);
    }
}
