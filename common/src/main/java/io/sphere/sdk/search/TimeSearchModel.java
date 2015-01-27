package io.sphere.sdk.search;

import java.time.LocalTime;
import java.util.Optional;

public class TimeSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, LocalTime>, SearchSortingModel<T> {

    public TimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, LocalTime> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofTime());
    }

    @Override
    public RangeTermFacetSearchModel<T, LocalTime> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofTime());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
