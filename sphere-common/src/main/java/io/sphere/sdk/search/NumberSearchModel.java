package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class NumberSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends RangeTermModelImpl<T, S, BigDecimal> implements SearchSortModel<T, S> {

    public NumberSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public RangedFilterSearchModel<T, BigDecimal> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofNumber());
    }

    @Override
    public RangedFacetSearchModel<T, BigDecimal> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofNumber());
    }

    @Override
    public S sorted() {
        return sortBuilder.apply(this);
    }
}
