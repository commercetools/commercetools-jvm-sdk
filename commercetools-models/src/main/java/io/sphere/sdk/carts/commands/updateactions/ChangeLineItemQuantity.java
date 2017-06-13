package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateActionImpl;
import org.javamoney.moneta.Money;

import javax.annotation.Nullable;

/**
 Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#changeLineItemQuantity()}
 */
public final class ChangeLineItemQuantity extends UpdateActionImpl<Cart> {
    private final String lineItemId;
    private final Long quantity;
    @Nullable
    private final Money externalPrice;
    @Nullable
    private final ExternalLineItemTotalPrice externalTotalPrice;

    private ChangeLineItemQuantity(final String lineItemId, final Long quantity, @Nullable final Money externalPrice, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        super("changeLineItemQuantity");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
        this.externalPrice = externalPrice;
        this.externalTotalPrice = externalTotalPrice;
    }

    private static ChangeLineItemQuantity of(final String lineItemId, final Long quantity, @Nullable final Money externalPrice, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return new ChangeLineItemQuantity(lineItemId, quantity, externalPrice, externalTotalPrice);
    }

    public static UpdateAction<Cart> of(final LineItem lineItem, final long quantity) {
        return of(lineItem.getId(), quantity);
    }

    public static ChangeLineItemQuantity of(final String lineItemId, final long quantity) {
        return of(lineItemId, quantity, null, null);
    }

    public static ChangeLineItemQuantity ofLineItemAndExternalPrice(final LineItem lineItem, final long quantity, @Nullable final Money externalPrice) {
        return ofLineItemAndExternalPrice(lineItem.getId(), quantity, externalPrice);
    }

    public static ChangeLineItemQuantity ofLineItemAndExternalPrice(final String lineItemId, final long quantity, @Nullable final Money externalPrice) {
        return of(lineItemId, quantity, externalPrice, null);
    }

    public static ChangeLineItemQuantity ofLineItemAndExternalTotalPrice(final LineItem lineItem, final long quantity, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return ofLineItemAndExternalTotalPrice(lineItem.getId(), quantity, externalTotalPrice);
    }

    public static ChangeLineItemQuantity ofLineItemAndExternalTotalPrice(final String lineItemId, final long quantity, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return of(lineItemId, quantity, null, externalTotalPrice);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public long getQuantity() {
        return quantity;
    }

    @Nullable
    public Money getExternalPrice() {
        return externalPrice;
    }

    @Nullable
    public ExternalLineItemTotalPrice getExternalTotalPrice() {
        return externalTotalPrice;
    }
}
