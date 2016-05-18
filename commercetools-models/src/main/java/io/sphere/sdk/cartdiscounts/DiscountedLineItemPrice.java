package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.money.MonetaryAmount;
import java.util.List;

@JsonDeserialize(as = DiscountedLineItemPriceImpl.class)
public interface DiscountedLineItemPrice {
    static DiscountedLineItemPrice of(final MonetaryAmount money, final List<DiscountedLineItemPortion> includedDiscounts) {
        return new DiscountedLineItemPriceImpl(money, includedDiscounts);
    }

    List<DiscountedLineItemPortion> getIncludedDiscounts();

    MonetaryAmount getValue();
}
