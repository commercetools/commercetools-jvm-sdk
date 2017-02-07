package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;

import javax.annotation.Nullable;

public final class RemoveTextLineItem extends UpdateActionImpl<ShoppingList> {
    private final String textLineItemId;
    @Nullable
    private final Long quantity;

    private RemoveTextLineItem(final String textLineItemId, @Nullable final Long quantity) {
        super("removeTextLineItem");
        this.textLineItemId = textLineItemId;
        this.quantity = quantity;
    }

    public static RemoveTextLineItem of(final String lineItemId, @Nullable final Long quantity) {
        return new RemoveTextLineItem(lineItemId, quantity);
    }

    public static RemoveTextLineItem of(final String lineItemId) {
        return of(lineItemId, null);
    }

    public static RemoveTextLineItem of(final TextLineItem lineItem, @Nullable final Long quantity) {
        return of(lineItem.getId(), quantity);
    }

    public static RemoveTextLineItem of(final TextLineItem lineItem) {
        return of(lineItem.getId());
    }

    public String getTextLineItemId() {
        return textLineItemId;
    }

    @Nullable
    public Long getQuantity() {
        return quantity;
    }
}
