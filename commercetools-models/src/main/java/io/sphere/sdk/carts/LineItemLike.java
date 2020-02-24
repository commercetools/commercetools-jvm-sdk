package io.sphere.sdk.carts;

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

    MonetaryAmount getTotalPrice();

    @Nullable
    TaxedItemPrice getTaxedPrice();

    /**
     *
     * Gets discount information for groups of line items.
     *
     * See also <a href="https://stackoverflow.com/a/35576069/5320693" target="_blank">more infos at stackoverflow</a>.
     *
     * @return discount infos
     */
    List<DiscountedLineItemPriceForQuantity> getDiscountedPricePerQuantity();
}
