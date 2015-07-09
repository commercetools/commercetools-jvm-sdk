package io.sphere.sdk.models;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public final class DefaultCurrencyUnits {
    private DefaultCurrencyUnits() {
    }

    public static final CurrencyUnit EUR = ofCode("EUR");
    public static final CurrencyUnit USD = ofCode("USD");

    private static CurrencyUnit ofCode(final String currencyCode) {
        return Monetary.getCurrency(currencyCode);
    }
}