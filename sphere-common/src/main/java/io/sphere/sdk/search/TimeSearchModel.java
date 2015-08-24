package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.LocalTime;

public class TimeSearchModel<T, S extends SearchSortDirection> extends RangeTermModelImpl<T, LocalTime> implements SearchSortingModel<T, S> {

    public TimeSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, LocalTime> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofTime());
    }

    @Override
    public RangedFacetSearchModel<T, LocalTime> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofTime());
    }

    @Override
    public SearchSort<T> sorted(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
