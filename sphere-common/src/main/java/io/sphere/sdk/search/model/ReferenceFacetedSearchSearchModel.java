package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class ReferenceFacetedSearchSearchModel<T> extends SearchModelImpl<T> {

    ReferenceFacetedSearchSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetedSearchSearchModel<T> id() {
        return new StringSearchModel<>(this, "id").facetedAndFiltered();
    }
}
