package io.sphere.sdk.search;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Arrays.asList;

public class MoneyAmountSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, BigDecimal>, SearchSortingModel<T> {

    public MoneyAmountSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, BigDecimal> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofMoneyAmount());
    }

    @Override
    public RangeTermFacetSearchModel<T, BigDecimal> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofMoneyAmount());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        if (hasPath(asList("variants", "price", "centAmount"))) {
            return new SphereSearchSort<>(new MoneyAmountSearchModel<>(Optional.empty(), "price"), sortDirection);
        } else {
            return new SphereSearchSort<>(this, sortDirection);
        }
    }


}
