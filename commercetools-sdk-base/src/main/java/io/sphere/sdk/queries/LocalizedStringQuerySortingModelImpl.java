package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.util.Locale;

final class LocalizedStringQuerySortingModelImpl<T> extends QueryModelImpl<T> implements LocalizedStringQuerySortingModel<T>, LocalizedStringOptionalQueryModel<T> {
    public LocalizedStringQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> lang(final Locale locale) {
        return locale(locale);
    }

    @Override
    public StringQuerySortingModel<T> locale(final Locale locale) {
        return stringModel(locale.toLanguageTag());
    }

    public static <T> LocalizedStringQuerySortingModel<T> of(@Nullable final QueryModel<T> queryModel, @Nullable final String pathSegment) {
        return new LocalizedStringQuerySortingModelImpl<>(queryModel, pathSegment);
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isNotPresentPredicate();
    }
}