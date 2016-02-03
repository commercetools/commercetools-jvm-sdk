package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.Locale;

public final class LocalizedStringFacetedSearchSearchModel<T> extends SearchModelImpl<T> {

    LocalizedStringFacetedSearchSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetedSearchSearchModel<T> locale(final Locale locale) {
        return new StringSearchModel<>(this, locale.toLanguageTag()).facetedAndFiltered();
    }
}