package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

/**
 * Cart discount value of an absolute amount.
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandIntegrationTest#changeValue()}
 *
 * @see CartDiscountValue
 * @see CartDiscount#getValue()
 * @see io.sphere.sdk.cartdiscounts.commands.updateactions.ChangeValue
 *
 */
public final class AbsoluteCartDiscountValue extends Base implements CartDiscountValue {
    private final List<MonetaryAmount> money;

    @JsonCreator
    private AbsoluteCartDiscountValue(final List<MonetaryAmount> money) {
        this.money = money;
    }

    public List<MonetaryAmount> getMoney() {
        return money;
    }

    public static AbsoluteCartDiscountValue of(final MonetaryAmount money) {
        return of(Collections.singletonList(money));
    }

    public static AbsoluteCartDiscountValue of(final List<MonetaryAmount> money) {
        return new AbsoluteCartDiscountValue(money);
    }
}
