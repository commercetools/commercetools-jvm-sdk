package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class DateSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends RangeTermModelImpl<T, S, LocalDate> implements SearchSortModel<T, S> {

    public DateSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public RangedFilterSearchModel<T, LocalDate> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofDate());
    }

    @Override
    public RangedFacetSearchModel<T, LocalDate> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofDate());
    }

    @Override
    public S sorted() {
        return sortBuilder.apply(this);
    }
}
