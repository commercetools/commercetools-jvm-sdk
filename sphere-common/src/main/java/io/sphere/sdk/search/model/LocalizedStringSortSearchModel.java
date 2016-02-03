package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.Locale;

public final class LocalizedStringSortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    LocalizedStringSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SearchSortFactory<T, S> searchSortFactory) {
        super(parent, pathSegment, searchSortFactory);
    }

    public S locale(final Locale locale) {
        return sorted(locale.toLanguageTag());
    }
}