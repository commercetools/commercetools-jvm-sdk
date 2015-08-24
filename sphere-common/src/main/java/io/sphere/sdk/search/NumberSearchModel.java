package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class NumberSearchModel<T, S extends SearchSortDirection> extends RangeTermModelImpl<T, BigDecimal> implements SearchSortingModel<T, S> {

    public NumberSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
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
    public SearchSort<T> sorted(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
