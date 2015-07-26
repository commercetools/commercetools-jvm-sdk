package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class DateSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, LocalDate>, SearchSortingModel<T, S> {

    public DateSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, LocalDate> filterBy() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofDate());
    }

    @Override
    public RangedFacetSearchModel<T, LocalDate> facetOf() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofDate());
    }

    @Override
    public SearchSort<T> sort(final S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
