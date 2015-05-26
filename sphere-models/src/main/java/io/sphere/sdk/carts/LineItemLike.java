package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;

import java.util.Optional;
import java.util.Set;

public interface LineItemLike {
    String getId();

    Set<ItemState> getState();

    long getQuantity();

    Optional<DiscountedLineItemPrice> getDiscountedPrice();
}
