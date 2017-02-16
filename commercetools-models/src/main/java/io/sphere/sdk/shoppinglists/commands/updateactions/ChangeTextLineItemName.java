package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;

/**
 * Changes the text line item name.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#changeTextLineItemName()}
 *
 * @see ShoppingList#getTextLineItems()
 */
public final class ChangeTextLineItemName extends UpdateActionImpl<ShoppingList> {
    private final String textLineItemId;
    private final LocalizedString name;

    private ChangeTextLineItemName(final String textLineItemId, final LocalizedString name) {
        super("changeTextLineItemName");
        this.textLineItemId = textLineItemId;
        this.name = name;
    }

    public static ChangeTextLineItemName of(final String textLineItemId, final LocalizedString name) {
        return new ChangeTextLineItemName(textLineItemId, name);
    }

    public static ChangeTextLineItemName of(final TextLineItem textLineItem, final LocalizedString name) {
        return of(textLineItem.getId(), name);
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }

    public LocalizedString getName() {
        return name;
    }
}
