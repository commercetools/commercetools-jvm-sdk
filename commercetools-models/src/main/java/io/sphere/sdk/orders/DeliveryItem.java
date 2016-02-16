package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.LineItemLike;

/**
 * Identifies line items or custom line items and they quanity for a {@link Delivery}.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#addDelivery()}
 *
 * @see io.sphere.sdk.orders.commands.updateactions.AddDelivery
 */
@JsonDeserialize(as = DeliveryItemImpl.class)
public interface DeliveryItem {
    /**
     * The ID of a {@link io.sphere.sdk.carts.LineItem} or a {@link io.sphere.sdk.carts.CustomLineItem}.
     * @return ID
     */
    String getId();

    /**
     * The amount of items of with ID {@link #getId()} in a {@link Delivery}.
     * @return quantity
     */
    Long getQuantity();

    @JsonIgnore
    static DeliveryItem of(final String id, final long quantity) {
        return of(id, Long.valueOf(quantity));
    }

    @JsonIgnore
    static DeliveryItem of(final String id, final Long quantity) {
        return new DeliveryItemImpl(id, quantity);
    }

    @JsonIgnore
    static DeliveryItem of(final LineItemLike lineItem, final Long quantity) {
        return of(lineItem.getId(), quantity);
    }

    @JsonIgnore
    static DeliveryItem of(final LineItemLike lineItem) {
        return of(lineItem, lineItem.getQuantity());
    }
}
