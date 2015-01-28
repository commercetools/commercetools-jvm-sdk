package io.sphere.sdk.search;

import java.math.BigDecimal;
import java.util.Optional;

public class NumberSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, BigDecimal>, SearchSortingModel<T> {

    public NumberSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, BigDecimal> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofNumber());
    }

    @Override
    public RangeTermFacetSearchModel<T, BigDecimal> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofNumber());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
