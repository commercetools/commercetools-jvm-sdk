package io.sphere.sdk.search;

import java.time.LocalDate;
import java.util.Optional;

public class DateSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, LocalDate>, SearchSortingModel<T, S> {

    public DateSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, LocalDate> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofDate());
    }

    @Override
    public RangeTermFacetSearchModel<T, LocalDate> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofDate());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
