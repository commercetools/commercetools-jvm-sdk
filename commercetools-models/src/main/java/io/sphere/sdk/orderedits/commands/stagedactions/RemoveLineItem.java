package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.ItemShippingDetailsDraft;
import io.sphere.sdk.carts.LineItem;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class RemoveLineItem extends OrderEditStagedUpdateActionBase {

    private final String lineItemId;

    @Nullable
    private final Long quantity;

    @Nullable
    private final MonetaryAmount externalPrice;

    @Nullable
    private final ExternalLineItemTotalPrice externalTotalPrice;

    @Nullable
    private final ItemShippingDetailsDraft shippingDetailsToRemove;

    @JsonCreator
    private RemoveLineItem(final String lineItemId, @Nullable final Long quantity,
                                     @Nullable final MonetaryAmount externalPrice,
                                     @Nullable final ExternalLineItemTotalPrice externalTotalPrice,
                                     @Nullable final ItemShippingDetailsDraft shippingDetailsToRemove) {
        super("removeLineItem");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
        this.externalPrice = externalPrice;
        this.externalTotalPrice = externalTotalPrice;
        this.shippingDetailsToRemove = shippingDetailsToRemove;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public Long getQuantity() {
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

    @Nullable
    public ItemShippingDetailsDraft getShippingDetailsToRemove() {
        return shippingDetailsToRemove;
    }

    /**
     * Creates a new object initialized with the given values.
     *
     * @param lineItemId initial value for the  property
     * @param quantity initial value for the  property
     * @param externalPrice initial value for the  property
     * @param externalTotalPrice initial value for the  property
     * @param shippingDetailsToRemove initial value for the  property
     * @return new object initialized with the given values
     */
    public static RemoveLineItem of(final String lineItemId,
                                    @Nullable final Long quantity,
                                    @Nullable final MonetaryAmount externalPrice,
                                    @Nullable final ExternalLineItemTotalPrice externalTotalPrice,
                                    @Nullable final ItemShippingDetailsDraft shippingDetailsToRemove) {
        return new RemoveLineItem(lineItemId, quantity, externalPrice, externalTotalPrice, shippingDetailsToRemove);
    }

    /**
     * Creates a copied update action initialized with the given parameter, the rest of the parameters are copied from the original object.
     *
     * @return new object initialized with the copied values from the original object
     */
    public RemoveLineItem withQuantity(final Long quantity) {
        return new RemoveLineItem(getLineItemId(), quantity, getExternalPrice(), getExternalTotalPrice(), getShippingDetailsToRemove());
    }

    /**
     * Creates a copied update action initialized with the given parameter, the rest of the parameters are copied from the original object.
     *
     * @return new object initialized with the copied values from the original object
     */
    public RemoveLineItem withExternalPrice(final MonetaryAmount externalPrice) {
        return new RemoveLineItem(getLineItemId(), getQuantity(), externalPrice, getExternalTotalPrice(), getShippingDetailsToRemove());
    }

    /**
     * Creates a copied update action initialized with the given parameter, the rest of the parameters are copied from the original object.
     *
     * @return new object initialized with the copied values from the original object
     */
    public RemoveLineItem withExternalTotalPrice(final ExternalLineItemTotalPrice externalTotalPrice) {
        return new RemoveLineItem(getLineItemId(), getQuantity(), getExternalPrice(), externalTotalPrice, getShippingDetailsToRemove());
    }

    /**
     * Creates a copied update action initialized with the given parameter, the rest of the parameters are copied from the original object.
     *
     * @return new object initialized with the copied values from the original object
     */
    public RemoveLineItem withShippingDetailsToRemove(final ItemShippingDetailsDraft shippingDetailsToRemove) {
        return new RemoveLineItem(getLineItemId(), getQuantity(), getExternalPrice(), getExternalTotalPrice(), shippingDetailsToRemove);
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