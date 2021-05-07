package io.sphere.sdk.utils;

import org.javamoney.moneta.spi.JDKCurrencyProvider;

final class CurrencyUtils {
    static final JDKCurrencyProvider CURRENCY_PROVIDER = new JDKCurrencyProvider();
}
