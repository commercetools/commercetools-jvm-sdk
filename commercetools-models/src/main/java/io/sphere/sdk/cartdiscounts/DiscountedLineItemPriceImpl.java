package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.List;

final class DiscountedLineItemPriceImpl extends Base implements DiscountedLineItemPrice {
    private final MonetaryAmount value;
    private final List<DiscountedLineItemPortion> includedDiscounts;

    @JsonCreator
    DiscountedLineItemPriceImpl(final MonetaryAmount value, final List<DiscountedLineItemPortion> includedDiscounts) {
        this.includedDiscounts = includedDiscounts;
        this.value = value;
    }

    public List<DiscountedLineItemPortion> getIncludedDiscounts() {
        return includedDiscounts;
    }

    public MonetaryAmount getValue() {
        return value;
    }
}
