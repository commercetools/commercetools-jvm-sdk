package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.LineItem;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class ChangeLineItemQuantity extends OrderEditStagedUpdateActionBase {

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