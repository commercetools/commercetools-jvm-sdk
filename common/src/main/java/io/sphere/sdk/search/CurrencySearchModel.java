package io.sphere.sdk.search;

import javax.money.CurrencyUnit;
import java.util.Optional;

public class CurrencySearchModel<T> extends SearchModelImpl<T> implements TermModel<T, CurrencyUnit>, SearchSortingModel<T> {

    public CurrencySearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, CurrencyUnit> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofCurrency());
    }

    @Override
    public TermFacetSearchModel<T, CurrencyUnit> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.empty(), TypeParser.ofCurrency());
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}