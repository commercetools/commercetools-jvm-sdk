package io.sphere.sdk.models;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

public final class DefaultCurrencyUnits {
    private DefaultCurrencyUnits() {
    }

    public static final CurrencyUnit EUR = MonetaryCurrencies.getCurrency("EUR");
}
