package io.sphere.sdk.search;

import java.time.LocalTime;
import java.util.Optional;

public class TimeSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, LocalTime>, SearchSortingModel<T, S> {

    public TimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, LocalTime> filterBy() {
        return new RangedFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofTime());
    }

    @Override
    public RangedFacetSearchModel<T, LocalTime> facetOf() {
        return new RangedFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofTime());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
