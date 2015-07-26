package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static java.util.Arrays.asList;

public class MoneyAmountSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements RangeTermModel<T, BigDecimal>, SearchSortingModel<T, S> {

    public MoneyAmountSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, BigDecimal> filterBy() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofMoneyAmount());
    }

    @Override
    public RangedFacetSearchModel<T, BigDecimal> facetOf() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofMoneyAmount());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        if (hasPath(asList("variants", "price", "centAmount"))) {
            return new SphereSearchSort<>(new MoneyAmountSearchModel<>(null, "price"), sortDirection);
        } else {
            return new SphereSearchSort<>(this, sortDirection);
        }
    }


}
