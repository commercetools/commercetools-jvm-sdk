package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.util.Locale;

public class LocalizedStringsQuerySortingModel<T> extends QueryModelImpl<T> implements LocalizedStringsQueryModel<T> {
    public LocalizedStringsQuerySortingModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> lang(final Locale locale) {
        return stringModel(locale.toLanguageTag());
    }

    public static <T> LocalizedStringsQuerySortingModel<T> of(@Nullable final QueryModel<T> queryModel, @Nullable final String pathSegment) {
        return new LocalizedStringsQuerySortingModel<>(queryModel, pathSegment);
    }
}