package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;

/**
 * Sets the text line item description.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#setTextLineItemDescription()}
 *
 * @see ShoppingList#getTextLineItems()
 */
public final class SetTextLineItemDescription extends UpdateActionImpl<ShoppingList> {
    private final String textLineItemId;
    private final LocalizedString description;

    private SetTextLineItemDescription(final String textLineItemId, final LocalizedString description) {
        super("setTextLineItemDescription");
        this.textLineItemId = textLineItemId;
        this.description = description;
    }

    public static SetTextLineItemDescription of(final String textLineItemId, final LocalizedString description) {
        return new SetTextLineItemDescription(textLineItemId, description);
    }

    public static SetTextLineItemDescription of(final TextLineItem textLineItem, final LocalizedString description) {
        return of(textLineItem.getId(), description);
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }

    public LocalizedString getDescription() {
        return description;
    }
}
