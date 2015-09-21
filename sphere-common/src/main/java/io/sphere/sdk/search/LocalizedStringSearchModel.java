package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.Locale;

public class LocalizedStringSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableSearchModel<T, S> {

    public LocalizedStringSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    public StringSearchModel<T, S> locale(final Locale locale) {
        return new StringSearchModel<>(this, locale.toLanguageTag(), sortBuilder);
    }
}