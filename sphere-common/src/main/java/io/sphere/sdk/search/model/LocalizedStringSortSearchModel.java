package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.Locale;

public class LocalizedStringSortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    public LocalizedStringSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SearchSortBuilder<T, S> searchSortBuilder) {
        super(parent, pathSegment, searchSortBuilder);
    }

    public S locale(final Locale locale) {
        return sorted(locale.toLanguageTag());
    }
}