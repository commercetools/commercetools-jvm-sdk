package io.sphere.sdk.productdiscounts;

import javax.money.MonetaryAmount;
import java.util.List;

import static java.util.Arrays.asList;

public class AbsoluteProductDiscount extends ProductDiscountValue {
    private final List<MonetaryAmount> money;

    private AbsoluteProductDiscount(final List<MonetaryAmount> money) {
        this.money = money;
    }

    /**
     * The array contains money values in different currencies. An absolute product discount will only match a price if this array contains a value with the same currency. If it contains 10€ and 15$, the matching € price will be decreased by 10€ and the matching $ price will be decreased by 15$.
     * @return list of discount values
     */
    public List<MonetaryAmount> getMoney() {
        return money;
    }

    public static AbsoluteProductDiscount of(final List<MonetaryAmount> money) {
        return new AbsoluteProductDiscount(money);
    }

    public static AbsoluteProductDiscount of(final MonetaryAmount money) {
        return new AbsoluteProductDiscount(asList(money));
    }
}

