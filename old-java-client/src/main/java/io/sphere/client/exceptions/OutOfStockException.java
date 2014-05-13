package io.sphere.client.exceptions;

import com.google.common.collect.ImmutableList;

import java.util.List;

/** Exception thrown when creating an order. */
public class OutOfStockException extends SphereException {
    private final ImmutableList<String> lineItemsIds;

    public OutOfStockException(Iterable<String> lineItemIds) {
        super("Some line items are out of stock. See OutOfStockException.getLineItemIds() to get their ids.");
        this.lineItemsIds = ImmutableList.copyOf(lineItemIds);
    }

    /** Ids of the line items that are out of stock. */
    public List<String> getLineItemIds() { return lineItemsIds; }
}
