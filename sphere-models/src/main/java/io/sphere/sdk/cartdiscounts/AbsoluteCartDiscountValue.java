package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

public class AbsoluteCartDiscountValue extends Base implements CartDiscountValue {
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
