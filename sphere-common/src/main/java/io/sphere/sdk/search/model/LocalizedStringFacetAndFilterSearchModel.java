package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.Locale;

public class LocalizedStringFacetAndFilterSearchModel<T> extends SearchModelImpl<T> {

    LocalizedStringFacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetAndFilterSearchModel<T> locale(final Locale locale) {
        return new StringSearchModel<>(this, locale.toLanguageTag()).facetedAndFiltered();
    }
}