package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;

import javax.annotation.Nullable;

/**
 * Removes a text line item from a shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#removeTextLineItem()}
 *
 * @see ShoppingList#getTextLineItems()
 */
public final class RemoveTextLineItem extends UpdateActionImpl<ShoppingList> {
    private final String textLineItemId;
    @Nullable
    private final Long quantity;

    private RemoveTextLineItem(final String textLineItemId, @Nullable final Long quantity) {
        super("removeTextLineItem");
        this.textLineItemId = textLineItemId;
        this.quantity = quantity;
    }

    public static RemoveTextLineItem of(final String textLineItemId) {
        return new RemoveTextLineItem(textLineItemId, null);
    }

    public static RemoveTextLineItem of(final TextLineItem textLineItem) {
        return of(textLineItem.getId());
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }

    @Nullable
    public Long getQuantity() {
        return quantity;
    }

    public RemoveTextLineItem withQuantity(@Nullable final Long quantity) {
        return new RemoveTextLineItem(getTextLineItemId(), quantity);
    }
}
