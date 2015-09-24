package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;
import java.util.Set;

public interface LineItemLike extends Custom {
    String getId();

    Set<ItemState> getState();

    Long getQuantity();

    @Nullable
    DiscountedLineItemPrice getDiscountedPrice();
}
