package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;

import javax.annotation.Nullable;
import java.util.Set;

public interface LineItemLike {
    String getId();

    Set<ItemState> getState();

    Long getQuantity();

    @Nullable
    DiscountedLineItemPrice getDiscountedPrice();
}
