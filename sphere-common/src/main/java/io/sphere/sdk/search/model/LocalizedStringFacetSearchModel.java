package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.Locale;

public class LocalizedStringFacetSearchModel<T> extends SearchModelImpl<T> {

    public LocalizedStringFacetSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetSearchModel<T, String> locale(final Locale locale) {
        return new StringSearchModel<>(this, locale.toLanguageTag()).faceted();
    }
}