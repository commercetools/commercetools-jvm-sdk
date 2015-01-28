package io.sphere.sdk.search;

import java.util.Optional;

public class StringSearchModel<T> extends SearchModelImpl<T> implements TermModel<T, String>, SearchSortingModel<T> {

    public StringSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, String> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofText());
    }

    @Override
    public TermFacetSearchModel<T, String> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofText());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}