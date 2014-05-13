package io.sphere.client.exceptions;

import com.google.common.collect.ImmutableList;

import java.util.List;

/** Exception thrown when creating an order. */
public class PriceChangedException extends SphereException {
    private final ImmutableList<String> lineItemsIds;
    
    public PriceChangedException(Iterable<String> lineItemIds) {
        super("The price, tax or shipping of some line items changed since they were added to cart." +
              "See PriceChangedException.getLineItemIds() to get their ids.");
        this.lineItemsIds = ImmutableList.copyOf(lineItemIds);
    }
    
    /** Ids of the line items for which the price, tax or shipping changed. */
    public List<String> getLineItemIds() { return lineItemsIds; }
}