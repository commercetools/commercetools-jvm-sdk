package io.sphere.sdk.search;

import java.math.BigDecimal;
import java.util.Optional;

public class NumberSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, BigDecimal>, SearchSortingModel<T, S> {

    public NumberSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, BigDecimal> filterBy() {
        return new RangedFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofNumber());
    }

    @Override
    public RangedFacetSearchModel<T, BigDecimal> facetOf() {
        return new RangedFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofNumber());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
