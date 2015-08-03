package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;

final class CountryQueryModelImpl<T> extends QueryModelImpl<T> implements CountryQueryModel<T> {
    public CountryQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final CountryCode countryCode) {
        return isPredicate(countryCode.getAlpha2());
    }
}
