package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

public interface CountryQueryModel<T> extends EqualityQueryModel<T, CountryCode> {
    @Override
    QueryPredicate<T> is(CountryCode countryCode);
}
