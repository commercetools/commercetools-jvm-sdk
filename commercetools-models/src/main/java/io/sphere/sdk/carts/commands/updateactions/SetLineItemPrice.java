package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import org.javamoney.moneta.Money;

import javax.annotation.Nullable;

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
    final private Money externalPrice;

    private SetLineItemPrice(final String lineItemId, final Money externalPrice) {
        super("setLineItemPrice");
        this.lineItemId = lineItemId;
        this.externalPrice = externalPrice;
    }

    public static SetLineItemPrice of(final String lineItemId, @Nullable final Money externalPrice) {
        return new SetLineItemPrice(lineItemId, externalPrice);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public Money getExternalPrice() {
        return externalPrice;
    }
}
