package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

public class ReferenceSearchModel<T, R> extends SearchModelImpl<T> implements TermModel<T, Referenceable<R>> {

    public ReferenceSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, Referenceable<R>> filterBy() {
        return new FilterSearchModel<>(Optional.of(this), Optional.of("id"), TypeSerializer.<R>ofReference());
    }

    @Override
    public FacetSearchModel<T, Referenceable<R>> facetOf() {
        return new FacetSearchModel<>(Optional.of(this), Optional.of("id"), TypeSerializer.<R>ofReference());
    }
}
