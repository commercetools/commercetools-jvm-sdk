package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.ItemShippingDetailsDraft;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 Decreases the quantity of the given line item. If after the update the quantity of the line item is not greater than 0 or the quantity is not specified, the line item is removed from the cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#removeLineItem()}
 */
public final class RemoveLineItem extends AbstractRemoveLineItem {

    RemoveLineItem(final String lineItemId, @Nullable final Long quantity,
                           @Nullable final MonetaryAmount externalPrice,
                           @Nullable final ExternalLineItemTotalPrice externalTotalPrice,
                           @Nullable final ItemShippingDetailsDraft shippingDetailsToRemove)  {
        super(lineItemId,quantity,externalPrice,externalTotalPrice,shippingDetailsToRemove);
    }

    public static RemoveLineItem of(final String lineItemId, @Nullable final Long quantity) {
        return of(lineItemId, quantity, null, null, null);
    }

    public static RemoveLineItem of(final String lineItemId) {
        return of(lineItemId, null);
    }

    public static RemoveLineItem of(final LineItem lineItem, @Nullable final Long quantity) {
        return of(lineItem.getId(), quantity);
    }

    public static RemoveLineItem of(final LineItem lineItem) {
        return of(lineItem.getId());
    }

    public static RemoveLineItem ofLineItemAndExternalPrice(final LineItem lineItem, @Nullable final Long quantity, @Nullable final MonetaryAmount externalPrice) {
        return ofLineItemAndExternalPrice(lineItem.getId(), quantity, externalPrice);
    }

    public static RemoveLineItem ofLineItemAndExternalPrice(final String lineItemId, @Nullable final Long quantity, @Nullable final MonetaryAmount externalPrice) {
        return of(lineItemId, quantity, externalPrice, null, null);
    }

    public static RemoveLineItem ofLineItemAndExternalTotalPrice(final LineItem lineItem, @Nullable final Long quantity, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return ofLineItemAndExternalTotalPrice(lineItem.getId(), quantity, externalTotalPrice);
    }

    public static RemoveLineItem ofLineItemAndExternalTotalPrice(final String lineItemId, @Nullable final Long quantity, @Nullable final ExternalLineItemTotalPrice externalTotalPrice) {
        return of(lineItemId, quantity, null, externalTotalPrice, null);
    }
}
