package io.sphere.sdk.queries;

import java.util.Locale;
import java.util.Optional;

public class LocalizedStringQuerySortingModel<T> extends EmbeddedQueryModel<T> implements SortingModel<T>, LocalizedStringQueryModel<T> {
    public LocalizedStringQuerySortingModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<T> lang(final Locale locale) {
        return new StringQuerySortingModel<>(Optional.of(this), locale.toLanguageTag());
    }

    @Override
    public Sort<T> sort(final SortDirection sortDirection) {
        return new SphereSort<>(this, sortDirection);
    }
}