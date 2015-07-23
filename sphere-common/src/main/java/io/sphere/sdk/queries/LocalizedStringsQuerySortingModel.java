package io.sphere.sdk.queries;

import java.util.Locale;

public class LocalizedStringsQuerySortingModel<T> extends QueryModelImpl<T> implements LocalizedStringsQueryModel<T> {
    public LocalizedStringsQuerySortingModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> lang(final Locale locale) {
        return stringModel(locale.toLanguageTag());
    }

    public static <T> LocalizedStringsQuerySortingModel<T> of(final QueryModel<T> queryModel, final String pathSegment) {
        return new LocalizedStringsQuerySortingModel<>(queryModel, pathSegment);
    }
}