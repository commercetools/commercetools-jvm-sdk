package io.sphere.sdk.models;

import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.money.CurrencyContextBuilder;
import javax.money.CurrencyUnit;

public final class DefaultCurrencyUnits {
    private DefaultCurrencyUnits() {
    }

    public static final CurrencyUnit EUR = ofCode("EUR");
    public static final CurrencyUnit USD = ofCode("USD");

    private static CurrencyUnit ofCode(final String currencyCode) {
        return CurrencyUnitBuilder.of(currencyCode, CurrencyContextBuilder.of("default").build()).build();
    }
}