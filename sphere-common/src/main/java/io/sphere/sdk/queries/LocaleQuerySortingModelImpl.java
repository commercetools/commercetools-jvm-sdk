package io.sphere.sdk.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.utils.IterableUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

final class LocaleQuerySortingModelImpl<T> extends QueryModelImpl<T> implements LocaleQuerySortingModel<T> {
    public LocaleQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }


    @Override
    public QueryPredicate<T> is(final Locale value) {
        return isPredicate(value.toLanguageTag());
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<Locale> args) {
        final List<String> LocalesStringList = IterableUtils.toStream(args)
                .map(v -> v.toLanguageTag())
                .map(code -> '"' + code + '"')
                .collect(toList());
        return isInPredicate(LocalesStringList);
    }

    @Override
    public QueryPredicate<T> isNot(final Locale element) {
        return isNotPredicate(element.toLanguageTag());
    }

    @Override
    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }

    @Deprecated
    @Override
    public QuerySort<T> sort(final QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
    }
}
