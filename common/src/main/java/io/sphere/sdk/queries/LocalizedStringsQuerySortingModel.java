package io.sphere.sdk.queries;

import java.util.Locale;
import java.util.Optional;

public class LocalizedStringsQuerySortingModel<T> extends QueryModelImpl<T> implements LocalizedStringsQueryModel<T> {
    public LocalizedStringsQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> lang(final Locale locale) {
        return new StringQuerySortingModel<>(Optional.of(this), locale.toLanguageTag());
    }

    public static <T> LocalizedStringsQuerySortingModel<T> of(final QueryModel<T> queryModel, final String pathSegment) {
        return new LocalizedStringsQuerySortingModel<>(Optional.of(queryModel), Optional.of(pathSegment));
    }
}