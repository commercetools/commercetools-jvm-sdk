package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;

import javax.annotation.Nullable;

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
    @Nullable
    private final LocalizedString description;

    private SetTextLineItemDescription(final String textLineItemId, @Nullable final LocalizedString description) {
        super("setTextLineItemDescription");
        this.textLineItemId = textLineItemId;
        this.description = description;
    }

    public static SetTextLineItemDescription of(final String textLineItemId) {
        return new SetTextLineItemDescription(textLineItemId, null);
    }

    public static SetTextLineItemDescription of(final TextLineItem textLineItem) {
        return of(textLineItem.getId());
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public SetTextLineItemDescription withDescription(@Nullable final LocalizedString description) {
        return new SetTextLineItemDescription(getTextLineItemId(), description);
    }
}
