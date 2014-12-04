package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

import java.util.Optional;

public class CountryQueryModel<T> extends QueryModelImpl<T> {
    public CountryQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> is(final CountryCode countryCode) {
        return new EqPredicate<>(this, countryCode.getAlpha2());
    }
}
