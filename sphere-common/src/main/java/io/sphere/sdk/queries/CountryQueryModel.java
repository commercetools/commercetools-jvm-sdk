package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

import java.util.Optional;

public class CountryQueryModel<T> extends QueryModelImpl<T> implements EqualityQueryModel<T, CountryCode> {
    public CountryQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final CountryCode countryCode) {
        return EqQueryPredicate.of(this, countryCode.getAlpha2());
    }
}
