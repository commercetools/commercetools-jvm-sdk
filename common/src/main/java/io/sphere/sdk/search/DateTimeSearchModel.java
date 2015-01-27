package io.sphere.sdk.search;

import java.time.LocalDateTime;
import java.util.Optional;

public class DateTimeSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, LocalDateTime>, SearchSortingModel<T> {

    public DateTimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, LocalDateTime> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofDateTime());
    }

    @Override
    public RangeTermFacetSearchModel<T, LocalDateTime> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofDateTime());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
