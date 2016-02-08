package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.List;

public final class DiscountedLineItemPrice extends Base {
    private final MonetaryAmount value;
    private final List<DiscountedLineItemPortion> includedDiscounts;

    @JsonCreator
    private DiscountedLineItemPrice(final MonetaryAmount value, final List<DiscountedLineItemPortion> includedDiscounts) {
        this.includedDiscounts = includedDiscounts;
        this.value = value;
    }

    public static DiscountedLineItemPrice of(final MonetaryAmount money, final List<DiscountedLineItemPortion> includedDiscounts) {
        return new DiscountedLineItemPrice(money, includedDiscounts);
    }

    public List<DiscountedLineItemPortion> getIncludedDiscounts() {
        return includedDiscounts;
    }

    public MonetaryAmount getValue() {
        return value;
    }
}
