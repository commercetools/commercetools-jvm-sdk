package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.commands.UpdateAction;

/**
 Removes the custom line item from the cart.

 {@include.example io.sphere.sdk.carts.CartIntegrationTest#removeCustomLineItemUpdateAction()}

 */
public class RemoveCustomLineItem extends UpdateAction<Cart> {
    private final String customLineItemId;

    public RemoveCustomLineItem(final String customLineItemId) {
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
