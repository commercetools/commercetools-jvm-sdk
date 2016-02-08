package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.toList;

final class CountryQueryModelImpl<T> extends QueryModelImpl<T> implements CountryQueryModel<T> {
    public CountryQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final CountryCode countryCode) {
        return isPredicate(countryCode.getAlpha2());
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<CountryCode> args) {
        final List<String> countryCodesStringList = toStream(args)
                .map(CountryCode::getAlpha2)
                .map(code -> '"' + code + '"')
                .collect(toList());
        return isInPredicate(countryCodesStringList);
    }

    @Override
    public QueryPredicate<T> isNot(final CountryCode countryCode) {
        return isNotPredicate(countryCode.getAlpha2());
    }
}
