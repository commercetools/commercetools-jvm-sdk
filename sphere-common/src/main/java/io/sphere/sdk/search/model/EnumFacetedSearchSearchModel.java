package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class EnumFacetedSearchSearchModel<T> extends SearchModelImpl<T> {

    EnumFacetedSearchSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetedSearchSearchModel<T> key() {
        return new StringSearchModel<>(this, "key").facetedAndFiltered();
    }

    public TermFacetedSearchSearchModel<T> label() {
        return new StringSearchModel<>(this, "label").facetedAndFiltered();
    }
}
