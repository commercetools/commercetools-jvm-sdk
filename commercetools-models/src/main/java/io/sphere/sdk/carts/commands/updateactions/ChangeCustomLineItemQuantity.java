package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 Sets the quantity of the given custom line item.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#changeCustomLineItemQuantity()}

 @see CustomLineItem#getQuantity()
 */
public final class ChangeCustomLineItemQuantity extends UpdateActionImpl<Cart> {
    private final String customLineItemId;
    private final Long quantity;

    private ChangeCustomLineItemQuantity(final String customLineItemId, final Long quantity) {
        super("changeCustomLineItemQuantity");
        this.customLineItemId = customLineItemId;
        this.quantity = quantity;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public static ChangeCustomLineItemQuantity of(final String lineItemId, final long quantity) {
        return new ChangeCustomLineItemQuantity(lineItemId, quantity);
    }

    public static UpdateAction<Cart> of(final CustomLineItem lineItem, final long quantity) {
        return of(lineItem.getId(), quantity);
    }
}
