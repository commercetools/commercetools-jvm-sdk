package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

public interface CountryQueryModel<T> extends EqualityQueryModel<T, CountryCode>,
        NotEqualQueryModel<T, CountryCode>,
        IsInQueryModel<T, CountryCode> {
    @Override
    QueryPredicate<T> is(CountryCode countryCode);

    @Override
    QueryPredicate<T> isIn(final Iterable<CountryCode> args);

    @Override
    QueryPredicate<T> isNot(final CountryCode element);
}
