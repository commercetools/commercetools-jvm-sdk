package io.sphere.sdk.search;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyBound extends Bound<Money> {

    private MoneyBound(final Money endpoint, final BoundType type) {
        super(endpoint, type);
    }

    @Override
    public String render() {
        return moneyToCents(endpoint()).toString();
    }

    /**
     * Creates a bound with the given endpoint, excluded from the range.
     * @param endpoint the endpoint value of the given type T.
     * @return the exclusive bound with the endpoint.
     */
    public static MoneyBound of(Money endpoint) {
        return new MoneyBound(endpoint, BoundType.EXCLUSIVE);
    }

    /*
    /**
     * Creates a bound with the given endpoint, included from the range.
     * @param endpoint the endpoint value of the given type T.
     * @return the inclusive bound with the endpoint.
     */
    /*NOT IMPLEMENTED YET
    public static MoneyBound inclusive(Money endpoint) {
        return new MoneyBound(endpoint, BoundType.INCLUSIVE);
    }*/

    // TODO move to external class to reuse
    private static BigDecimal moneyToCents(Money money) {
        return money.getNumberStripped().movePointRight(2).setScale(0, RoundingMode.HALF_EVEN);
    }
}
