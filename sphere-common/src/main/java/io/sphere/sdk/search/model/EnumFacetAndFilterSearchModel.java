package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class EnumFacetAndFilterSearchModel<T> extends SearchModelImpl<T> {

    EnumFacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetAndFilterSearchModel<T> key() {
        return new StringSearchModel<>(this, "key").facetedAndFiltered();
    }

    public TermFacetAndFilterSearchModel<T> label() {
        return new StringSearchModel<>(this, "label").facetedAndFiltered();
    }
}
