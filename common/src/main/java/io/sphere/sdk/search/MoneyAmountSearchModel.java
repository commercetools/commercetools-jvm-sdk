package io.sphere.sdk.search;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class MoneyAmountSearchModel<T> extends RangeTermSearchModel<T, Money> {

    public MoneyAmountSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final Money value) {
        return toCents(value).toPlainString();
    }

    /**
     * Converts the given money amount to cent amount (e.g. from "20,00 EUR" to "2000").
     * @param money the amount to be converted.
     * @return the cent amount.
     */
    private static BigDecimal toCents(Money money) {
        return money.getNumberStripped().movePointRight(2).setScale(0, RoundingMode.HALF_EVEN);
    }
}
