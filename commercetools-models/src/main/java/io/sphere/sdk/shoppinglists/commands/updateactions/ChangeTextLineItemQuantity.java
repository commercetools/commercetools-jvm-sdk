package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;

/**
 * Changes the text line item quantity.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#changeTextLineItemQuantity()}
 *
 * @see ShoppingList#getTextLineItems()
 */
public final class ChangeTextLineItemQuantity extends UpdateActionImpl<ShoppingList> {
    private final String textLineItemId;
    private final Long quantity;

    private ChangeTextLineItemQuantity(final String textLineItemId, final Long quantity) {
        super("changeTextLineItemQuantity");
        this.textLineItemId = textLineItemId;
        this.quantity = quantity;
    }

    public static ChangeTextLineItemQuantity of(final String textLineItemId, final Long quantity) {
        return new ChangeTextLineItemQuantity(textLineItemId, quantity);
    }

    public static ChangeTextLineItemQuantity of(final TextLineItem textLineItem, final Long quantity) {
        return of(textLineItem.getId(), quantity);
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
