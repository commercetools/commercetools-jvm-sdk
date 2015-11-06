package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class ReferenceFacetAndFilterSearchModel<T> extends SearchModelImpl<T> {

    ReferenceFacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetAndFilterSearchModel<T, String> id() {
        return new StringSearchModel<>(this, "id").facetedAndFiltered();
    }
}
