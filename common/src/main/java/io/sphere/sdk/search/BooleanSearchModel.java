package io.sphere.sdk.search;

import java.util.Optional;

public class BooleanSearchModel<T> extends SearchModelImpl<T> implements TermModel<T, Boolean>, SearchSortingModel<T> {

    public BooleanSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, Boolean> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public TermFacetSearchModel<T, Boolean> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }

    private String render(final boolean value) {
        return value ? "true" : "false";
    }
}
