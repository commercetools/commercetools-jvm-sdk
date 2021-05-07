package io.sphere.sdk.utils;

import org.javamoney.moneta.internal.JDKCurrencyProvider;

final class CurrencyUtils {
    static final JDKCurrencyProvider CURRENCY_PROVIDER = new JDKCurrencyProvider();
}
