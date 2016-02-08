package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class LocalizedEnumFacetedSearchSearchModel<T> extends SearchModelImpl<T> {

    LocalizedEnumFacetedSearchSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetedSearchSearchModel<T> key() {
        return new StringSearchModel<>(this, "key").facetedAndFiltered();
    }

    public LocalizedStringFacetedSearchSearchModel<T> label() {
        return new LocalizedStringFacetedSearchSearchModel<>(this, "label");
    }
}
