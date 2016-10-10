package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 Sets the totalPrice and price of the line item and
 changes the priceMode of the line item to ExternalTotal.
 If the external total price of the line item was already set and
 the externalTotalPrice field of this update action is not given,
 the external price is unset and the line item price mode is set back to Platform.

 Although both price and totalPrice are set with this update action,
 only totalPrice will be used to calculate the total price of the cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setLineItemTotalPrice()}

 */
public final class SetLineItemTotalPrice extends UpdateActionImpl<Cart> {
    @Nullable
    private final ExternalLineItemTotalPrice externalTotalPrice;
    private final String lineItemId;

    private SetLineItemTotalPrice(final String lineItemId, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        super("setLineItemTotalPrice");
        this.lineItemId = lineItemId;
        this.externalTotalPrice = externalTotalPrice;
    }

    public static SetLineItemTotalPrice of(final String lineItemId, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return new SetLineItemTotalPrice(lineItemId, externalTotalPrice);
    }

    @Nullable
    public ExternalLineItemTotalPrice getExternalTotalPrice() {
        return externalTotalPrice;
    }

    public String getLineItemId() {
        return lineItemId;
    }
}
