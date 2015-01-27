package io.sphere.sdk.search;

import java.time.LocalDate;
import java.util.Optional;

public class DateSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, LocalDate>, SearchSortingModel<T> {

    public DateSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, LocalDate> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofDate());
    }

    @Override
    public RangeTermFacetSearchModel<T, LocalDate> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofDate());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
