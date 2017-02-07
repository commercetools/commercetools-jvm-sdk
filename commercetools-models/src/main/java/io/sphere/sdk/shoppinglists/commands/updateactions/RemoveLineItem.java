package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;

/**
 * Removes a line item.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#removeLineItem()}
 *
 * @see ShoppingList#getLineItems()
 */
public final class RemoveLineItem extends UpdateActionImpl<ShoppingList> {
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
