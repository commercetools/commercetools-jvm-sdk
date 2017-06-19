package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Sets the price of a line item and changes the priceMode of the line item to ExternalPrice.
 * If the price mode of the line item is ExternalPrice and no externalPrice is given, the external price is unset
 * and the priceMode is set to Platform.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setLineItemExternalPrice()}
 */
public final class SetLineItemPrice extends UpdateActionImpl<Cart> {

    final private String lineItemId;
    @Nullable
    final private MonetaryAmount externalPrice;

    private SetLineItemPrice(final String lineItemId, @Nullable final MonetaryAmount externalPrice) {
        super("setLineItemPrice");
        this.lineItemId = lineItemId;
        this.externalPrice = externalPrice;
    }

    public static SetLineItemPrice of(final LineItem lineItem) {
        return of(lineItem.getId());
    }

    public static SetLineItemPrice of(final String lineItemId) {
        return of(lineItemId, null);
    }

    public static SetLineItemPrice of(final LineItem lineItem, @Nullable final MonetaryAmount externalPrice) {
        return of(lineItem.getId(), externalPrice);
    }

    public static SetLineItemPrice of(final String lineItemId, @Nullable final MonetaryAmount externalPrice) {
        return new SetLineItemPrice(lineItemId, externalPrice);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public MonetaryAmount getExternalPrice() {
        return externalPrice;
    }
}
