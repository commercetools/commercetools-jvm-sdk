package io.sphere.sdk.search;

import org.javamoney.moneta.Money;

import java.util.Optional;

public class MoneyAmountSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, Money>, SearchSortingModel<T> {

    public MoneyAmountSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, Money> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofMoneyAmount());
    }

    @Override
    public RangeTermFacetSearchModel<T, Money> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofMoneyAmount());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
