package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class ReferenceFacetSearchModelImpl<T> extends SearchModelImpl<T> implements ReferenceFacetSearchModel<T> {

    protected ReferenceFacetSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFacetSearchModel<T, String> id() {
        return new StringSearchModel<>(this, "id").faceted();
    }
}
