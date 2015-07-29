package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.util.Locale;

final class LocalizedStringsQuerySortingModelImpl<T> extends QueryModelImpl<T> implements LocalizedStringsQuerySortingModel<T> {
    public LocalizedStringsQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> lang(final Locale locale) {
        return stringModel(locale.toLanguageTag());
    }

    public static <T> LocalizedStringsQuerySortingModel<T> of(@Nullable final QueryModel<T> queryModel, @Nullable final String pathSegment) {
        return new LocalizedStringsQuerySortingModelImpl<>(queryModel, pathSegment);
    }
}