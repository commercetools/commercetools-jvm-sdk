package io.sphere.sdk.search;

import java.util.Currency;
import java.util.Optional;

public class CurrencySearchModel<T> extends TermSearchModel<T, Currency> {

    public CurrencySearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final Currency value) {
        return "\"" + currencyToString(value) + "\"";
    }

    /**
     * Converts the given currency to string format.
     * @param currency the currency instance.
     * @return the currency as string.
     */
    private static String currencyToString(final Currency currency) {
        return currency.getCurrencyCode();
    }
}