package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;

public class ReferenceSearchModel<T, R> extends SearchModelImpl<T> implements TermModel<T, Referenceable<R>> {

    public ReferenceSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, Referenceable<R>> filterBy() {
        return new FilterSearchModel<>(this, "id", TypeSerializer.<R>ofReference());
    }

    @Override
    public FacetSearchModel<T, Referenceable<R>> facetOf() {
        return new FacetSearchModel<>(this, "id", TypeSerializer.<R>ofReference());
    }
}
