package io.sphere.sdk.search;

import java.time.LocalTime;
import java.util.Optional;

public class TimeSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, LocalTime>, SearchSortingModel<T, S> {

    public TimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, LocalTime> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofTime());
    }

    @Override
    public RangeTermFacetSearchModel<T, LocalTime> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofTime());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
