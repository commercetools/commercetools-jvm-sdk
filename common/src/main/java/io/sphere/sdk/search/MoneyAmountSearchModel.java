package io.sphere.sdk.search;

import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class MoneyAmountSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, Money>, SearchSortingModel<T> {

    public MoneyAmountSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, Money> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofMoneyAmount());
    }

    @Override
    public RangeTermFacetSearchModel<T, Money> facet() {
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
