package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Arrays.asList;

public class MoneyCentAmountSearchModel<T, S extends SearchSortDirection> extends RangeTermModelImpl<T, Long> implements SearchSortingModel<T, S> {

    public MoneyCentAmountSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, Long> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofMoneyCentAmount());
    }

    @Override
    public RangedFacetSearchModel<T, Long> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofMoneyCentAmount());
    }

    @Override
    public SearchSort<T> sorted(S sortDirection) {
        if (hasPath(asList("variants", "price", "centAmount"))) {
            return new SphereSearchSort<>(new MoneyCentAmountSearchModel<>(null, "price"), sortDirection);
        } else {
            return new SphereSearchSort<>(this, sortDirection);
        }
    }

}
