package io.sphere.sdk.queries;

import javax.money.CurrencyUnit;

public interface CurrencyCodeQueryModel<T> extends QueryModel<T>, EqualityQueryModel<T, String> {
    @Override
    QueryPredicate<T> is(final String currencyCode);

    default QueryPredicate<T> is(final CurrencyUnit currencyUnit) {
        return is(currencyUnit.getCurrencyCode());
    }
}
