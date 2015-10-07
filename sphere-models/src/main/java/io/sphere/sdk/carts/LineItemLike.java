package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * <p>Interface for common stuff of {@link LineItem}s and {@link CustomLineItem}s.</p>
 */
public interface LineItemLike extends Custom {
    String getId();

    Set<ItemState> getState();

    Long getQuantity();

    @Nullable
    DiscountedLineItemPrice getDiscountedPrice();
}
