package io.sphere.sdk.search;

import java.time.ZonedDateTime;
import java.util.Optional;

public class DateTimeSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, ZonedDateTime>, SearchSortingModel<T, S> {

    public DateTimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, ZonedDateTime> filterBy() {
        return new RangedFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofDateTime());
    }

    @Override
    public RangedFacetSearchModel<T, ZonedDateTime> facetOf() {
        return new RangedFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofDateTime());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
