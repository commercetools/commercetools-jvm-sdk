package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

/**
 *
 * An absolute discount reduces the matching price by a fixed amount (for example 10€ off).
 * If more than one product discount matches a price, the discount sort order determines which one will be applied.
 *
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandIntegrationTest#changeValue()}
 *
 * @see io.sphere.sdk.productdiscounts.ProductDiscount
 * @see ProductDiscount#getValue()
 * @see io.sphere.sdk.productdiscounts.commands.updateactions.ChangeValue
 */
public final class AbsoluteProductDiscountValue extends Base implements ProductDiscountValue {
    private final List<MonetaryAmount> money;

    @JsonCreator
    private AbsoluteProductDiscountValue(final List<MonetaryAmount> money) {
        this.money = money;
    }

    /**
     * The array contains money values in different currencies. An absolute product discount will only match a price if this array contains a value with the same currency. If it contains 10€ and 15$, the matching € price will be decreased by 10€ and the matching $ price will be decreased by 15$.
     * @return list of discount values
     */
    public List<MonetaryAmount> getMoney() {
        return money;
    }

    public static AbsoluteProductDiscountValue of(final List<MonetaryAmount> money) {
        return new AbsoluteProductDiscountValue(money);
    }

    public static AbsoluteProductDiscountValue of(final MonetaryAmount money) {
        return new AbsoluteProductDiscountValue(Collections.singletonList(money));
    }
}

