package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class ReferenceFacetSearchModel<T> extends SearchModelImpl<T> {

    public ReferenceFacetSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetSearchModel<T, String> id() {
        return new StringSearchModel<>(this, "id").faceted();
    }
}
