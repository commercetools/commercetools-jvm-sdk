package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 Removes the custom line item from the cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#removeCustomLineItem()}

 */
public final class RemoveCustomLineItem extends UpdateActionImpl<Cart> {
    private final String customLineItemId;

    private RemoveCustomLineItem(final String customLineItemId) {
        super("removeCustomLineItem");
        this.customLineItemId = customLineItemId;
    }

    public static RemoveCustomLineItem of(final String customLineItemId) {
        return new RemoveCustomLineItem(customLineItemId);
    }

    public static RemoveCustomLineItem of(final CustomLineItem customLineItem) {
        return of(customLineItem.getId());
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}
