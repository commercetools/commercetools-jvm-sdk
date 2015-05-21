package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.List;

public class DiscountedLineItemPrice extends Base {
    private final MonetaryAmount money;
    private final List<DiscountedLineItemPortion> includedDiscounts;

    @JsonCreator
    private DiscountedLineItemPrice(final MonetaryAmount money, final List<DiscountedLineItemPortion> includedDiscounts) {
        this.includedDiscounts = includedDiscounts;
        this.money = money;
    }

    public static DiscountedLineItemPrice of(final MonetaryAmount money, final List<DiscountedLineItemPortion> includedDiscounts) {
        return new DiscountedLineItemPrice(money, includedDiscounts);
    }

    public List<DiscountedLineItemPortion> getIncludedDiscounts() {
        return includedDiscounts;
    }

    public MonetaryAmount getMoney() {
        return money;
    }
}
