package io.sphere.sdk.search;

import javax.money.CurrencyUnit;
import java.util.Optional;

public class CurrencySearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements TermModel<T, CurrencyUnit>, SearchSortingModel<T, S> {

    public CurrencySearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, CurrencyUnit> filter() {
        return new FilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofCurrency());
    }

    @Override
    public FacetSearchModel<T, CurrencyUnit> facet() {
        return new FacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofCurrency());
    }

    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}