package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart.<br/>

 In case of multiple shipping addresses, {@link ChangeLineItemQuantity} does not support changing the shipping details
 like {@link RemoveLineItem} and {@link AddLineItem} because in the case of not sending the {@code shippingDetails} field
 it would not be clear if the shipping details should be removed at all (possible data loss) or remain unchanged.
 Use {@link SetLineItemShippingDetails} in combination with this update action in one
 cart update command to change the line item quantity and shipping details altogether.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#changeLineItemQuantity()}
 */
public final class ChangeLineItemQuantity extends UpdateActionImpl<Cart> {
    private final String lineItemId;
    private final Long quantity;
    @Nullable
    private final MonetaryAmount externalPrice;
    @Nullable
    private final ExternalLineItemTotalPrice externalTotalPrice;

    private ChangeLineItemQuantity(final String lineItemId, final Long quantity, @Nullable final MonetaryAmount externalPrice, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        super("changeLineItemQuantity");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
        this.externalPrice = externalPrice;
        this.externalTotalPrice = externalTotalPrice;
    }

    private static ChangeLineItemQuantity of(final String lineItemId, final Long quantity, @Nullable final MonetaryAmount externalPrice, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return new ChangeLineItemQuantity(lineItemId, quantity, externalPrice, externalTotalPrice);
    }

    public static UpdateAction<Cart> of(final LineItem lineItem, final long quantity) {
        return of(lineItem.getId(), quantity);
    }

    public static ChangeLineItemQuantity of(final String lineItemId, final long quantity) {
        return of(lineItemId, quantity, null, null);
    }

    public static ChangeLineItemQuantity ofLineItemAndExternalPrice(final LineItem lineItem, final long quantity, @Nullable final MonetaryAmount externalPrice) {
        return ofLineItemAndExternalPrice(lineItem.getId(), quantity, externalPrice);
    }

    public static ChangeLineItemQuantity ofLineItemAndExternalPrice(final String lineItemId, final long quantity, @Nullable final MonetaryAmount externalPrice) {
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
    public MonetaryAmount getExternalPrice() {
        return externalPrice;
    }

    @Nullable
    public ExternalLineItemTotalPrice getExternalTotalPrice() {
        return externalTotalPrice;
    }
}
