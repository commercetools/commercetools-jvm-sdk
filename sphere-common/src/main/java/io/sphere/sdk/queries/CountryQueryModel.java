package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

public class CountryQueryModel<T> extends QueryModelImpl<T> implements EqualityQueryModel<T, CountryCode> {
    public CountryQueryModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final CountryCode countryCode) {
        return isPredicate(countryCode.getAlpha2());
    }
}
