package io.sphere.sdk.queries;

import java.util.Locale;
import java.util.Optional;

public class LocalizedStringQuerySortingModel<T> extends QueryModelImpl<T> implements LocalizedStringQueryModel<T> {
    public LocalizedStringQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> lang(final Locale locale) {
        return new StringQuerySortingModel<>(Optional.of(this), locale.toLanguageTag());
    }

    public static <T> LocalizedStringQuerySortingModel<T> of(final QueryModel<T> queryModel, final String pathSegment) {
        return new LocalizedStringQuerySortingModel<T>(Optional.of(queryModel), Optional.of(pathSegment));
    }
}