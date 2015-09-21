package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class DateTimeSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends RangeTermModelImpl<T, S, ZonedDateTime> implements SearchSortModel<T, S> {

    public DateTimeSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
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
    public S sorted() {
        return sortBuilder.apply(this);
    }
}
