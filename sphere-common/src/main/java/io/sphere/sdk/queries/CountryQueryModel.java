package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;

public class CountryQueryModel<T> extends QueryModelImpl<T> implements EqualityQueryModel<T, CountryCode> {
    public CountryQueryModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final CountryCode countryCode) {
        return isPredicate(countryCode.getAlpha2());
    }
}
