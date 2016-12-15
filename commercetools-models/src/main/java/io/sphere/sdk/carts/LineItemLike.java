package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.DiscountedLineItemPriceForQuantity;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;
import java.util.Optional;
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
     * See also <a href="http://stackoverflow.com/a/35576069/5320693" target="_blank">more infos at stackoverflow</a>.
     *
     * @return discount infos
     */
    List<DiscountedLineItemPriceForQuantity> getDiscountedPricePerQuantity();

    /**
     * Estimates the total price of the line item with taxes included, useful for B2C scenarios.
     *
     * Be aware that when taxes are not yet applied to the cart (i.e. shipping country/state are missing) then it cannot
     * be determined whether taxes are included or not in the calculation, as it depends on the particular tax category
     * linked to the product and the country/state of the cart.
     *
     * @return the estimated total gross price of the line item
     */
    default MonetaryAmount estimateTotalGrossPrice() {
        return Optional.ofNullable(getTaxedPrice())
                .map(TaxedItemPrice::getTotalGross)
                .orElseGet(this::getTotalPrice);
    }

    /**
     * Estimates the total price of the line item without taxes included, useful for B2B scenarios.
     *
     * Be aware that when taxes are not yet applied to the cart (i.e. shipping country/state are missing) then it cannot
     * be determined whether taxes are included or not in the calculation, as it depends on the particular tax category
     * linked to the product and the country/state of the cart.
     *
     * @return the estimated total net price of the line item
     */
    default MonetaryAmount estimateTotalNetPrice() {
        return Optional.ofNullable(getTaxedPrice())
                .map(TaxedItemPrice::getTotalNet)
                .orElseGet(this::getTotalPrice);
    }
}
