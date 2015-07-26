package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class DateTimeSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, ZonedDateTime>, SearchSortingModel<T, S> {

    public DateTimeSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, ZonedDateTime> filterBy() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofDateTime());
    }

    @Override
    public RangedFacetSearchModel<T, ZonedDateTime> facetOf() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofDateTime());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
