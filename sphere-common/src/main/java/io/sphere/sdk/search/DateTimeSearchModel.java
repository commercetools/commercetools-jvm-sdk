package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class DateTimeSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, ZonedDateTime>, SearchSortingModel<T, S> {

    public DateTimeSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, ZonedDateTime> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofDateTime());
    }

    @Override
    public RangedFacetSearchModel<T, ZonedDateTime> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofDateTime());
    }

    @Override
    public SearchSort<T> sorted(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
