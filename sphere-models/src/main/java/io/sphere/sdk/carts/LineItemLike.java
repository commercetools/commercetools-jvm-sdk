package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Set;

/**
 * <p>Interface for common stuff of {@link LineItem}s and {@link CustomLineItem}s.</p>
 */
public interface LineItemLike extends Custom {
    String getId();

    Set<ItemState> getState();

    Long getQuantity();

    /**
     *
     * @deprecated use {@link #getDiscountedPricePerQuantity()} instead
     * @return DiscountedLineItemPrice
     */
    @Nullable
    @Deprecated
    DiscountedLineItemPrice getDiscountedPrice();

    MonetaryAmount getTotalPrice();

    List<DiscountedLineItemPriceForQuantity> getDiscountedPricePerQuantity();
}
