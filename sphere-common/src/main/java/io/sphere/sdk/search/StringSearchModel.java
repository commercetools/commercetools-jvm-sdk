package io.sphere.sdk.search;

import java.util.Optional;

public class StringSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements TermModel<T, String>, SearchSortingModel<T, S> {

    public StringSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, String> filterBy() {
        return new FilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofText());
    }

    @Override
    public FacetSearchModel<T, String> facetOf() {
        return new FacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofText());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}