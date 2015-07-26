package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.LocalTime;

public class TimeSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, LocalTime>, SearchSortingModel<T, S> {

    public TimeSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, LocalTime> filterBy() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofTime());
    }

    @Override
    public RangedFacetSearchModel<T, LocalTime> facetOf() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofTime());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
