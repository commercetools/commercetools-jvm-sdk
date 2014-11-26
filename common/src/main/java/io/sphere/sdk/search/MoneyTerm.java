package io.sphere.sdk.search;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyTerm extends Term<Money> {

    private MoneyTerm(final Money value) {
        super(value);
    }

    @Override
    public String render() {
        return moneyToCents(value()).toString();
    }

    public static MoneyTerm of(Money money) {
        return new MoneyTerm(money);
    }

    // TODO move to external class to reuse
    private static BigDecimal moneyToCents(Money money) {
        return money.getNumberStripped().movePointRight(2).setScale(0, RoundingMode.HALF_EVEN);
    }
}
