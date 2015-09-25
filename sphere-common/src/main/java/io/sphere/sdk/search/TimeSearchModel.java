package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.LocalTime;

public class TimeSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableRangeTermModel<T, S, LocalTime> implements SearchSortModel<T, S> {

    public TimeSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
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
    public FacetedSearchModel<T> facetedSearch() {
        return super.facetedSearch();
    }

    @Override
    public S sorted() {
        return sortBuilder.apply(this);
    }
}
