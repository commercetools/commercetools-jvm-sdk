package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

public class ReferenceSearchModel<T, R> extends SearchModelImpl<T> implements TermModel<T, Referenceable<R>> {

    public ReferenceSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, Referenceable<R>> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.of("id"), TypeSerializer.<R>ofReference());
    }

    @Override
    public TermFacetSearchModel<T, Referenceable<R>> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.of("id"), TypeSerializer.<R>ofReference());
    }
}
