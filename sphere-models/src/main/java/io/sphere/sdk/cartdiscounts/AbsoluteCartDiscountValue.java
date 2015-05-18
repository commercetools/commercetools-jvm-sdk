package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.List;

public class AbsoluteCartDiscountValue extends Base implements CartDiscountValue {
    private final List<MonetaryAmount> money;

    @JsonCreator
    public AbsoluteCartDiscountValue(final List<MonetaryAmount> money) {
        this.money = money;
    }

    public List<MonetaryAmount> getMoney() {
        return money;
    }

    public AbsoluteCartDiscountValue of(final List<MonetaryAmount> money) {
        return new AbsoluteCartDiscountValue(money);
    }
}
